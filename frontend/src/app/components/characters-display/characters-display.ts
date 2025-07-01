import { Component } from '@angular/core';
import { characters } from './characters';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { ICharacter } from '../../interfaces/ICharacter';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-characters-display',
  imports: [CommonModule, MatCardModule],
  templateUrl: './characters-display.html',
  styleUrl: './characters-display.scss'
})
export class CharactersDisplay {

  public characters = characters;

  constructor(private authService: AuthService) {}

  public onCharacterClick(character: ICharacter): void {
    this.authService.setUser({
      id: '1',
      email: '',
      name: character.name,
      token: '', 
      refreshToken: ''
    })
  }
  
}
