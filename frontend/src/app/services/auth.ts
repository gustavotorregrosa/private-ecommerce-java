import { Injectable, OnInit } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private _isAuthenticated: boolean = false;
  public isAuthenticated = (): boolean => this._isAuthenticated; 

  constructor() {
    // setInterval(() => {
    //   console.log('Toggling authentication status');
    //   this._isAuthenticated = !this._isAuthenticated;
    // }, 5000);

   }
  
}
