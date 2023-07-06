package com.springsockets.playground.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EventObject {
    private String event;
    private Message message;
}

class Message {
    private String content;
    private UUID sender;
    private UUID receiver;

}