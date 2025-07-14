import { Injectable, OnInit } from '@angular/core';
import { IAuthenticatedUser } from '../interfaces/IUser';
import { HttpClient } from '@angular/common/http';
import { ConfigService } from './config';
import { IResponse } from '../interfaces/IResponse';
import { SocketService } from './socket';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private configService: ConfigService, private socketService: SocketService) { }

  public isAuthenticated = (): boolean => !!this._user;

  private _user: IAuthenticatedUser | null = null;

  public getUser = (): IAuthenticatedUser | null  => this._user;

  public setUser = (user: IAuthenticatedUser | null) => this._user = user;

  public login = async (email: string, password: string): Promise<void> => {

    const authenticatedUser = await new Promise<IAuthenticatedUser>((resolve, reject) => {
      const url: string = this.configService.getApiURL() + '/auth/login';
      this.http.post<IResponse<IAuthenticatedUser>>(url, { email, password }, {
        headers: {
          'socket-session-id': this.socketService.getSessionId() || ''
        }
      }).subscribe({
        next: (data) => {
          resolve(data.data);
        },
        error: (error: any) => {
          console.error('HTTP POST error:', error);
          reject(error);
        }
      });
    });
    
    this.setUser(authenticatedUser);

    // this.socketService.send(JSON.stringify({
    //   type: 'login',
    //   user:  authenticatedUser.user
       
    // }));
  }
  
}
