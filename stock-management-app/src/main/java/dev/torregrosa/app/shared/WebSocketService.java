package dev.torregrosa.app.shared;

import java.net.ServerSocket;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton") 
public class WebSocketService {

    private static final String WEBSOCKET_ENDPOINT = "/ws";
    private ServerSocket serverSocket;

    public WebSocketService() {
        // try {
        //     this.serverSocket = new ServerSocket(8080);
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     throw new RuntimeException("Failed to initialize WebSocket server", e);
        // }
    }

    public void initializeWebSocket() throws Exception {
        this.serverSocket = new ServerSocket(8080);


        System.out.println("WebSocket server initialized on port 8080");
        
      
    }

    
    public void sendMessage(String message) {
        // Implementation goes here
    }

    // Example method to handle incoming messages (to be implemented)
    public void onMessageReceived(String message) {
        // Implementation goes here
    }
}
