package com.springsockets.playground.sockets;

import com.fasterxml.jackson.databind.JsonNode;
import com.springsockets.playground.dto.MessageDTO;
import com.springsockets.playground.models.Message;
import com.springsockets.playground.payload.SocketResponse;
import com.springsockets.playground.services.IMessageService;
import com.springsockets.playground.utils.Identity;
import com.springsockets.playground.utils.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@ServerEndpoint("/socket")
public class MessageHandler implements WebSocketHandler {

    @Autowired
    private IMessageService messageService;
    private static final List<Identity> identities = new ArrayList<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        identities.add(new Identity(webSocketSession));
        System.out.println("[SOCKET EVENT]: New client connected to server socket. (" + webSocketSession.getId() + ")");
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        JsonNode eventObject = JsonParser.parseJson(webSocketMessage.getPayload().toString());
        String event = eventObject.get("event").asText();
        System.out.println(event);
        switch (event) {
            case "identity":
                Identity identity = identities.stream().filter(i -> i.getWebSocketSession().getId().equals(webSocketSession.getId())).findFirst().orElse(null);
                if (identity != null) {
                    identity.setId(UUID.fromString(eventObject.get("id").asText()));
                    System.out.println("[IDENTITY]: " + identity.getId() + " is now connected to the server.");
                }
                List<WebSocketSession> sessions = identities.stream().map(Identity::getWebSocketSession).collect(Collectors.toList());
                List<UUID> ids = identities.stream().map(Identity::getId).collect(Collectors.toList());
                for (WebSocketSession session : sessions) {
                    webSocketSession.sendMessage(new TextMessage(JsonParser.toJson(new SocketResponse("verified-identity", ids, "New identity connected to the server."))));
                    if (session.isOpen() && !session.getId().equals(webSocketSession.getId()))
                        session.sendMessage(new TextMessage(JsonParser.toJson(new SocketResponse("new-identity", identity.getId(), "New identity connected to the server."))));
                }
                break;

            case "new-message":
                UUID senderId = UUID.fromString(eventObject.get("message").get("sender").asText());
                UUID receiverId = UUID.fromString(eventObject.get("message").get("receiver").asText());
                String content = eventObject.get("message").get("content").asText();
                MessageDTO messageData = new MessageDTO(senderId, receiverId, content);
                System.out.println(messageData.toString());
                WebSocketSession receiverSession = Identity.getReceiverSession(identities, receiverId);
                if (receiverSession != null && receiverSession.isOpen()) {
                    receiverSession.sendMessage(new TextMessage(JsonParser.toJson(new SocketResponse("new-message", messageData, "Message sent successfully"))));
                }
                Message message = this.messageService.createMessage(messageData);
                System.out.println(message.toString());
                webSocketSession.sendMessage(new TextMessage(new SocketResponse("new-message", messageData, "Message sent successfully").toString()));
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
