import { Injectable } from "@angular/core";
import { ConfigService } from "./config";
import { ISocketMessage, ISocketTopic } from "../interfaces/ISocketMessage";

@Injectable({
  providedIn: 'root'
})
export class SocketService {
    private socket: WebSocket | null = null;
    private sessionId: string | null = null;

    private socketEvents: { 
        topic: ISocketTopic,
        fn: (message: ISocketMessage) => void
    
    }[] = []

    constructor(private configService: ConfigService) {

        this.socketEvents.push({
            topic: ISocketTopic.SET_SESSION_ID,
            fn: (message: ISocketMessage) => {
                this.sessionId = message.sessionId;
                console.log('Session ID set:', message.sessionId);
            }}
        )

    }

    public getSessionId(): string | null {
        return this.sessionId;
    }

    connect(): void {
        this.disconnect();
        const url = this.configService.getWSApiURL();
        this.socket = new WebSocket(url);

        this.socket.onopen = () => {
           
            const dummyMessage: ISocketMessage = {
                topic: ISocketTopic.SET_SESSION_ID,
                sessionId: this.sessionId || '123',
                message: 'ola mundoo...'
            }

            setTimeout(() => {
                console.log('WebSocket connected:', url);
                this.send(JSON.stringify(dummyMessage));
            }, 4000);

            this.socket!.onmessage = ({data}) => {
                console.log({data})
                try {
                    const socketEvent: ISocketMessage = JSON.parse(data);
                    this.socketEvents.filter(event => event.topic === socketEvent.topic).map(event => event.fn(socketEvent));
                } catch (error) {
                    console.error('Error parsing WebSocket message:', error);
                    return;
                }

            };
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