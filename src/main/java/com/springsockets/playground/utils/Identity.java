package com.springsockets.playground.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Identity {

    private UUID id;
    private WebSocketSession webSocketSession;

    public Identity(WebSocketSession webSocketSession) {
        this.id = UUID.randomUUID();
        this.webSocketSession = webSocketSession;
    }

    public static WebSocketSession getReceiverSession(List<Identity> identities, UUID receiverId){
        return Objects.requireNonNull(identities.stream().filter(i -> i.getId().equals(receiverId)).findFirst().orElse(null)).getWebSocketSession();
    }

}
