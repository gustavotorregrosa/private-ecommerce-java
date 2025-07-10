import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth';
import { ConfigService } from './config';
import { IAuthenticatedUser } from '../interfaces/IUser';
import { Observable } from 'rxjs';
import { IResponse } from '../interfaces/IResponse';


interface IRequestOptions {
    headers?: { [key: string]: string };
    body?: { [key: string]: string };
}

@Injectable({
    providedIn: 'root'
})
export class HttpService {

    constructor(private http: HttpClient, private authService: AuthService, private configService: ConfigService) { }

    public async get<T>(endpoint: string): Promise<T> {

        const url: string = this.configService.getApiURL() + endpoint;
        let response: T;

        try {
            response = await this.makeRequest<T>((url, requestParams) => this.http.get<T>(url, { headers: requestParams.headers }), url, { headers: this.getHeaders() });
            return response;
        } catch (error: any) {

            if(error.status == 401){
                await this.refreshToken()
                response = await this.makeRequest<T>((url, requestParams) => this.http.get<T>(url, { headers: requestParams.headers }), url, { headers: this.getHeaders() });
                return response;
                
            }

            throw error;
        }
    }

    public async post<T>(endpoint: string, body: any): Promise<T> {

        return await new Promise<T>((resolve, reject) => {
            this.http.post<T>(endpoint, body).subscribe({
                next: (data: T) => resolve(data),
                error: (error: any) => {
                    reject(error);
                }
            });
        });

    }


    private makeRequest<T>(fn: (url: string, requestParams: IRequestOptions) => Observable<T>, url: string, requestParams: IRequestOptions): Promise<T> {
        return new Promise<T>((resolve, reject) => {
            const headers = { ...this.getHeaders(), ...requestParams.headers}
            const body = {...requestParams.body}
            fn(url, { headers, body}).subscribe({
                next: (data: T) => resolve(data),
                error: (error: any) => reject(error)
            });
        });

    }


    private getHeaders(): { [key: string]: string } {
        const headers: { [key: string]: string } = {
            'Content-Type': 'application/json'
        };

        if (this.authService.isAuthenticated()) {
            headers['Authorization'] = `Bearer ${this.authService.getUser()?.token}`;
        }

        return headers;
    }

    private async refreshToken(): Promise<IAuthenticatedUser> {
        if (!this.authService.isAuthenticated()) {
            throw new Error('User is not authenticated');
        }

        const url: string = this.configService.getApiURL() + '/auth/refresh';

        return await new Promise<IAuthenticatedUser>((resolve, reject) => {
           this.http.get<IResponse<IAuthenticatedUser>>(url, {
            headers: {...this.getHeaders(), 'Authorization': `Bearer ${this.authService.getUser()?.refreshToken}`}
        }).subscribe({
            next: (data: IResponse<IAuthenticatedUser>) => {
                const user: IAuthenticatedUser = data.data;
                this.authService.setUser(user);
                resolve(user);
            },
            error: (error: any) => {
                reject(error);
            }
        })})

    }
}