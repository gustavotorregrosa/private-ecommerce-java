package dev.torregrosa.app.shared.socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    private List<WebSocketFunction> functions;

    public WebSocketHandler() {
        sessions = new HashMap<>();
        functions = new ArrayList<>();
    }

    public void addFunction(WebSocketFunction function) {
        functions.add(function);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket connection established: " + session.getRemoteAddress());
        System.out.println("Session ID: " + session.getId());
        System.out.println("Session URI: " + session.getUri());

        
   
       

        

        session.sendMessage(new TextMessage("Welcome to the WebSocket server!" + session.getId()));

        sessions.put(session.getId(), session);
        redisTemplate.convertAndSend("websocket-channel", "New WebSocket connection established: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Connection closed: " + session.getId());
        sessions.remove(session.getId());
        redisTemplate.convertAndSend(CHANNEL_NAME, "WebSocket connection closed: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message: " + message.getPayload() + " from session: " + session.getId());
        // session.sendMessage(new TextMessage("Message received"));

        for (String sessionId : sessions.keySet()) {
            System.out.println("Session ID: " + sessionId + ", Session: " + sessions.get(sessionId));

            WebSocketSession _session = sessions.get(sessionId);
            if (_session.isOpen()) {
                for (WebSocketFunction _function : functions) {
                    _function.setParams(_session, session.getId(), message.getPayload());
                    _function.run();
                    System.out.println("Function executed for session: " + sessionId);
                }
            } else {
                System.out.println("Session " + sessionId + " is not open.");
            }
        }


    }

}
