import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth';
import { CharactersDisplay } from '../../characters-display/characters-display';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  imports: [CharactersDisplay, CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home {

  constructor(private authService: AuthService) { }

  public isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

}
