package com.springsockets.playground.models;


import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Getter
@Setter
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "sender")
    private UUID sender;

    @Column(name = "receiver")
    private UUID receiver;

    @Column(name = "content")
    private String content;

    public Message(UUID sender, UUID receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }
}
