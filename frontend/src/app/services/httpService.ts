import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth';
import { ConfigService } from './config';

@Injectable({
    providedIn: 'root'
})
export class HttpService {

    constructor(private http: HttpClient, private authService: AuthService, private configService: ConfigService) { }

    public async get<T>(endpoint: string): Promise<T> {

        const url: string = this.configService.getApiURL() + endpoint;

        return await new Promise<T>((resolve, reject) => {
            console.log('token')
            console.log(this.authService.getUser())

            this.http.get<T>(url, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': this.authService.isAuthenticated() ? `Bearer ${this.authService.getUser()?.token}` : ''
                }
            }).subscribe({
                next: (data: T) => {
                    console.log('HTTP GET response:', {data});
                    resolve(data)
                },
                error: (error: any) => {
                    console.error('HTTP GET error:', {error});
                    reject(error);
                    // console.error('HTTP GET error:', error);
                    // throw new Error(`HTTP GET request failed: ${error.message}`);
                }
            });
        });

    }

    public async post<T>(endpoint: string, body: any): Promise<T> {

        return await new Promise<T>((resolve, reject) => {
            this.http.post<T>(endpoint, body).subscribe({
                next: (data: T) => resolve(data),
                error: (error: any) => {
                    reject(error);
                    // console.error('HTTP POST error:', error);
                    // throw new Error(`HTTP POST request failed: ${error.message}`);
                }
            });
        });

    }
}