import { Component } from '@angular/core';
import { characters } from './characters';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-characters-display',
  imports: [CommonModule],
  templateUrl: './characters-display.html',
  styleUrl: './characters-display.scss'
})
export class CharactersDisplay {

  public characters = characters;
  
}
