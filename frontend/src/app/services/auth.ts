import { Injectable, OnInit } from '@angular/core';
import { IAuthenticatedUser } from '../interfaces/IUser';
import { HttpService } from './httpService';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpService: HttpService) {}

  public isAuthenticated = (): boolean => !!this._user;

  private _user: IAuthenticatedUser | null = null;

  public getUser = (): IAuthenticatedUser | null  => this._user;

  public setUser = (user: IAuthenticatedUser | null) => this._user = user;

  public login = async (email: string, password: string): Promise<void> => {
    const authenticatedUser = await this.httpService.post<IAuthenticatedUser>('/auth/login', { email, password })
    console.log('Authenticated user:', authenticatedUser);
    this.setUser(authenticatedUser);
  }
  

}
