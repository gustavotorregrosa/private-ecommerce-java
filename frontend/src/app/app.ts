import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { GeneralLayout } from './components/general-layout/general-layout';
import { SocketService } from './services/socket';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, GeneralLayout],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App implements OnInit {
  
  protected title = 'Dunder Mifflin Paper Company';

  constructor(private socketService: SocketService) {}

  ngOnInit(): void {
    this.socketService.connect();
    this.socketService.onMessage((event) => {
      console.log({event});
      console.log('Message received from WebSocket:', event.data);
    });
  }
}
