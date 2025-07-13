import { Injectable } from "@angular/core";
import { ConfigService } from "./config";

@Injectable({
  providedIn: 'root'
})
export class SocketService {
    private socket: WebSocket | null = null;

    constructor(private configService: ConfigService) {}

    connect(): void {
     
        this.disconnect();

        // const url = this.configService.getApiURL().replace(/^http/, 'ws') + '/ws';
        const url = this.configService.getWSApiURL();
        this.socket = new WebSocket(url);

        this.socket.onopen = () => {
            console.log('WebSocket connected');
        };

        this.socket.onmessage = (event) => {
            console.log('WebSocket message received:', event.data);
            console.log({event})
        };

        this.socket.onclose = () => {
            console.log('WebSocket disconnected');
        };

        this.socket.onerror = (error) => {
            console.error('WebSocket error:', error);
        };
    }

    send(data: string | ArrayBufferLike | Blob | ArrayBufferView): void {
        if (this.socket && this.socket.readyState === WebSocket.OPEN) {
            this.socket.send(data);
        } else {
            console.warn('WebSocket is not open. Unable to send message.');
        }
    }

    onMessage(callback: (event: MessageEvent) => void): void {
        if (this.socket) {
            this.socket.onmessage = callback;
        }
    }

    disconnect(): void {
        if (this.socket) {
            this.socket.close();
            this.socket = null;
        }
    }
}