import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth';
import { CharactersDisplay } from '../../characters-display/characters-display';

@Component({
  selector: 'app-home',
  imports: [CharactersDisplay],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home {

  constructor(private authService: AuthService) {}

  get isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

}
