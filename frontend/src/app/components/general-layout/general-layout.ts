import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from '../navbar/navbar';

@Component({
  selector: 'app-general-layout',
  imports: [RouterOutlet, Navbar],
  templateUrl: './general-layout.html',
  styleUrl: './general-layout.scss'
})
export class GeneralLayout {
  
}
