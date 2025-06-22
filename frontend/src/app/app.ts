import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { GeneralLayout } from './components/general-layout/general-layout';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, GeneralLayout],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected title = 'frontend';
}
