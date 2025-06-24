import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  public theme: string = 'light';

  public toggleTheme = (): void => {
    this.theme = this.theme === 'light' ? 'dark' : 'light';
    // document.documentElement.setAttribute('data-theme', this.theme);
  }
  constructor() { }
}
