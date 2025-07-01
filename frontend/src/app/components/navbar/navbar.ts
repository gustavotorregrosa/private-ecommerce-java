import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import {Router} from '@angular/router';
import { ConfigService } from '../../services/config';
import { AuthService } from '../../services/auth';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  imports: [MatToolbarModule, MatButtonModule, MatIconModule, MatMenuModule, CommonModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss'
})
export class Navbar {

  private router = inject(Router);

  constructor(public configService: ConfigService, public authService: AuthService) {

    setTimeout(() => {
      console.log('Navbar initialized');
      console.log('Auth Service:', this.authService.isAuthenticated());
    }, 1000);

  }

  public navigateToCategories(){
    this.router.navigate(['/categories']);
  }

  public logout() {
    this.authService.setUser(null);
    this.router.navigate(['/']);
  }
  
}
