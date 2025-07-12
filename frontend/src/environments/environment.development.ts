export const development = {
    production: false,
    apiUrl: 'http://localhost:8080',
    socketUrl: 'http://localhost:8080/ws',
    authUrl: 'http://localhost:3000/auth',
    version: '1.0.0-dev',
    featureFlags: {
        enableNewUI: true,
        enableBetaFeatures: false,
    },
    logLevel: 'debug', // Options: 'debug', 'info', 'warn', 'error'
}