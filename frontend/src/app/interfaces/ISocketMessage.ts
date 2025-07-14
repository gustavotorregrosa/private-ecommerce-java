export enum ISocketTopic {
    SET_SESSION_ID = 'set-session-id',
    MESSAGE = 'message',
    ERROR = 'error'
}

export interface ISocketMessage {
    sessionId: string;
    message: string;
    topic: ISocketTopic
}