import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import {Router} from '@angular/router';
import { ConfigService } from '../../services/config';

@Component({
  selector: 'app-navbar',
  imports: [MatToolbarModule, MatButtonModule, MatIconModule, MatMenuModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss'
})
export class Navbar {

  private router = inject(Router);

  constructor(public configService: ConfigService) {}


  public navigateToCategories(){
    console.log('test...')
    this.router.navigate(['/categories']);
  }
  
}
