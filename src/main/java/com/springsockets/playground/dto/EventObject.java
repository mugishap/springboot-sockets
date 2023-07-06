package com.springsockets.playground.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EventObject {
    private String event;
    private MessageDTO message;
}

