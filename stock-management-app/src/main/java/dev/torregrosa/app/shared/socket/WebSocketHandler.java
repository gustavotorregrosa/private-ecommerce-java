package dev.torregrosa.app.shared.socket;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String CHANNEL_NAME = "websocket-channel";

    private HashMap<String, WebSocketSession> sessions;

    public WebSocketHandler() {
        sessions = new HashMap<>();
    }

    public void sendToRedis(WebSocketMessageTemplate message) {
        redisTemplate.convertAndSend(CHANNEL_NAME, message.toString());
    }

    public void sendToInstances(String messageFromRedis) {
        try {

            WebSocketMessageTemplate _message = WebSocketMessageTemplate.fromJson(messageFromRedis);

            switch (_message.topic) {
                case "new-login":
                    for (WebSocketSession session : sessions.values()) {
                         WebSocketMessageTemplate message = WebSocketMessageTemplate.fromJson(messageFromRedis);
                        if(session.getId().equals(message.sessionId)) {
                            message.message = "Welcome to Dunder Mifflin";
                            session.sendMessage(new TextMessage(message.toString()));
                        } else {
                            message.sessionId = session.getId();
                            session.sendMessage(new TextMessage(message.toString()));
                        }
                    }
                    
                    break;

                case "refresh-categories":
                case "refresh-products":
                    for (WebSocketSession session : sessions.values()) {
                        WebSocketMessageTemplate message = WebSocketMessageTemplate.fromJson(messageFromRedis);
                        session.sendMessage(new TextMessage(message.toString()));
                    }
                    
                    break;
                default:
                    System.out.println("Unknown topic: " + _message.topic);
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error processing message from Redis: " + e.getMessage());
            return;
        }
       
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        WebSocketMessageTemplate messageTemplate = new WebSocketMessageTemplate(
            "WebSocket connection established",
            session.getId(),
            "set-session-id"
        );

        session.sendMessage(new TextMessage(messageTemplate.toString()));
        sessions.put(session.getId(), session);

        // redisTemplate.convertAndSend("websocket-channel", "New WebSocket connection established: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Connection closed: " + session.getId());
        sessions.remove(session.getId());
        redisTemplate.convertAndSend(CHANNEL_NAME, "WebSocket connection closed: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload(); 
        WebSocketMessageTemplate socketMessage = WebSocketMessageTemplate.fromJson(payload);

        System.out.println("Received message: " + socketMessage.toString() + " from session: " + session.getId());
        System.out.println("topic...: " + socketMessage.topic + " - " + socketMessage.sessionId);

        
    }

}
