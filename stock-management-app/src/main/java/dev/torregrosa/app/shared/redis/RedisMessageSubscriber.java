package dev.torregrosa.app.shared.redis;

import org.springframework.stereotype.Component;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.Message;

@Component
public class RedisMessageSubscriber implements MessageListener {
	

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // Handle the message received from Redis
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());
        System.out.println("Received message: " + body + " from channel: " + channel);
    }

}
