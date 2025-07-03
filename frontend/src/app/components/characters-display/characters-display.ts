import { Component } from '@angular/core';
import { characters } from './characters';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { ICharacter } from '../../interfaces/ICharacter';
import { AuthService } from '../../services/auth';
import { ConfigService } from '../../services/config';  
import { MatTooltipModule } from '@angular/material/tooltip';
import { HttpService } from '../../services/httpService';

@Component({
  selector: 'app-characters-display',
  imports: [CommonModule, MatCardModule, MatTooltipModule],
  templateUrl: './characters-display.html',
  styleUrl: './characters-display.scss'
})
export class CharactersDisplay {

  public characters = characters;

  constructor(private authService: AuthService, private configService: ConfigService, private httpService: HttpService) {}

  public async onCharacterClick(character: ICharacter): Promise<void> {

    console.log('Character clicked:', this.configService.getApiURL());
    const result = await this.httpService.get<any>('/categories')
    console.log({result})

    
    this.authService.setUser({
      id: '1',
      email: character.email,
      name: character.name,
      token: '', 
      refreshToken: ''
    })
  }
  
}
