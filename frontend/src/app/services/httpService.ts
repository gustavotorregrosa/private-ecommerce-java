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

        // return await new Promise<T>((resolve, reject) => {
        //     console.log('token')
        //     console.log(this.authService.getUser())

        //     this.http.get<T>(url, {
        //         headers: {
        //             'Content-Type': 'application/json',
        //             'Authorization': this.authService.isAuthenticated() ? `Bearer ${this.authService.getUser()?.token}` : ''
        //         }
        //     }).subscribe({
        //         next: (data: T) => {
        //             console.log('HTTP GET response:', {data});
        //             resolve(data)
        //         },
        //         error: (error: any) => {
        //             console.error('HTTP GET error:', {error});
        //             reject(error);
        //             // console.error('HTTP GET error:', error);
        //             // throw new Error(`HTTP GET request failed: ${error.message}`);
        //         }
        //     });
        // });

    
        

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


    private getHeaders(): { [key: string]: string } {
        const headers: { [key: string]: string } = {
            'Content-Type': 'application/json'
        };

        if (this.authService.isAuthenticated()) {
            headers['Authorization'] = `Bearer ${this.authService.getUser()?.token}`;
        }

        return headers;
    }


    private async refreshToken(): Promise<void> {
        if (!this.authService.isAuthenticated()) {
            throw new Error('User is not authenticated');
        }

        // Logic to refresh the token if needed
        // This is a placeholder; actual implementation may vary
        const user = this.authService.getUser();
        if (user && user.token) {
            // Assume we have a method to check token validity and refresh it
            // await this.authService.refreshTokenIfNeeded();
        }
    }
}