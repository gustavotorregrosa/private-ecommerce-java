package dev.torregrosa.app.shared.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class WebSocketMessageTemplate {
    
    public String message;
    public String sessionId;
    public String topic;

    public static WebSocketMessageTemplate fromJson(String json) {

        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("JSON string cannot be null or empty");
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = (ObjectNode) mapper.readTree(json);


            String message = node.has("message") ? node.get("message").asText() : null;
            String sessionId = node.has("sessionId") ? node.get("sessionId").asText() : null;
            String topic = node.has("topic") ? node.get("topic").asText() : null;
            return new WebSocketMessageTemplate(message, sessionId, topic);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON to WebSocketMessageTemplate", e);
        }

        // ObjectMapper mapper = new ObjectMapper();
        // try {
        //     return mapper.readValue(json, WebSocketMessageTemplate.class);
        // } catch (Exception e) {
        //     throw new RuntimeException("Failed to parse JSON to WebSocketMessageTemplate", e);
        // }
    }

    public WebSocketMessageTemplate(String message, String sessionId,  String topic) {
        this.message = message;
        this.sessionId = sessionId;
        this.topic = topic;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonNode = mapper.createObjectNode();
        jsonNode.put("sessionId", sessionId);
        jsonNode.put("message", message);
        jsonNode.put("topic", topic);
        return jsonNode.toString();

    }
}
