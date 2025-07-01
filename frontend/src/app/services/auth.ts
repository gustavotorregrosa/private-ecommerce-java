import { Injectable, OnInit } from '@angular/core';
import { IUser } from '../interfaces/IUser';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public isAuthenticated = (): boolean => !!this._user;

  private _user: IUser | null = null;

  public getUser = (): IUser | null  => this._user;

  public setUser(user: IUser | null) {
    this._user = user;
 
  }

  constructor() {
    // setInterval(() => {
    //   console.log('Toggling authentication status');
    //   this._isAuthenticated = !this._isAuthenticated;
    // }, 5000);

   }
  
}
