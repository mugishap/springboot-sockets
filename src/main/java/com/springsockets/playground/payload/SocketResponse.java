package com.springsockets.playground.payload;

import com.springsockets.playground.dto.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocketResponse {

    private String event;
    private Object data;
    private String message;

}
