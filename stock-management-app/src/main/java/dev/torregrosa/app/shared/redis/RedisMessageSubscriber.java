package dev.torregrosa.app.shared.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import dev.torregrosa.app.shared.socket.WebSocketHandler;

@Component
public class RedisMessageSubscriber implements MessageListener {

    @Autowired
    WebSocketHandler webSocketHandler;

    public RedisMessageSubscriber(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }
	

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());

        if(channel.equals("websocket-channel")) {
            webSocketHandler.sendToInstances(body);
        }
       
    }

}
