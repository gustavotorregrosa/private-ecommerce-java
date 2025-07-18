export enum ISocketTopic {
    SET_SESSION_ID = 'set-session-id',
    NEW_LOGIN = 'new-login',
    REFRESH_CATEGORIES = 'refresh-categories',
    REFRESH_PRODUCTS = 'refresh-products',
    MESSAGE = 'message',
    ERROR = 'error'
}

export interface ISocketMessage {
    sessionId: string;
    message: string;
    topic: ISocketTopic
}