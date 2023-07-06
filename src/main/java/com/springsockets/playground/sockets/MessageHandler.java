package com.springsockets.playground.sockets;

import com.fasterxml.jackson.databind.JsonNode;
import com.springsockets.playground.utils.JsonParser;
import org.springframework.web.socket.*;

import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

@ServerEndpoint("/socket")
public class MessageHandler implements WebSocketHandler {

    private static final List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        sessions.add(webSocketSession);
        System.out.println("[SOCKET EVENT]: New client connected to server socket. (" + webSocketSession.getId() + ")");
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        JsonNode eventObject = JsonParser.parseJson(webSocketMessage.getPayload().toString());
        String event = eventObject.get("event").asText();
        System.out.println(event);
        switch (event) {
            case "new-message":
                System.out.println("[MESSAGE]: New message from " + eventObject.get("message").get("sender").asText() + " : " + eventObject.get("message").get("content").asText());
                break;
            case "delete-message":
                webSocketSession.sendMessage(webSocketMessage);
                break;
            default:
                webSocketSession.sendMessage(webSocketMessage);
                webSocketSession.sendMessage(new TextMessage("Invalid message received."));
                break;
        }


    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
