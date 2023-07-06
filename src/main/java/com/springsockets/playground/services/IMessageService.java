package com.springsockets.playground.services;

import com.springsockets.playground.dto.MessageDTO;
import com.springsockets.playground.models.Message;

import java.util.List;
import java.util.UUID;

public interface IMessageService {

    List<Message> getAllMessages(UUID senderId, UUID receiverId);

    Message getMessageById(UUID id);

    Message createMessage(MessageDTO dto);

    String deleteMessage(UUID id);

    Message updateMessage(UUID id, MessageDTO dto);

}