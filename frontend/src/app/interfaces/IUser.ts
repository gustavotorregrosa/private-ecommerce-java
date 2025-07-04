export interface IAuthenticatedUser {
    user: {
        id: string;
        email: string;
        name: string;
    }
    token: string;
    refreshToken: string;
}