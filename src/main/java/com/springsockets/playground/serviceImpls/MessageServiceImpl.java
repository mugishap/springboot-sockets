package com.springsockets.playground.serviceImpls;

import com.springsockets.playground.dto.MessageDTO;
import com.springsockets.playground.models.Message;
import com.springsockets.playground.repositories.IMessageRepository;
import com.springsockets.playground.services.IMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private final IMessageRepository messageRepository;

    @Override
    public List<Message> getAllMessages(UUID senderId, UUID receiverId) {
        return this.messageRepository.findMessagesByRoom(senderId, receiverId);
    }

    @Override
    public Message getMessageById(UUID id) {
        return this.messageRepository.findById(id).orElse(null);
    }

    @Override
    public Message createMessage(MessageDTO dto) {
        System.out.println("Creating message");
        return this.messageRepository.save(new Message(dto.getSender(), dto.getReceiver(), dto.getContent()));
    }

    @Override
    public String deleteMessage(UUID id) {
        return "Message deleted successfully.";
    }

    @Override
    public Message updateMessage(UUID id, MessageDTO dto) {
        Message message = this.messageRepository.findById(id).orElse(null);
        if (message != null) {
            message.setContent(dto.getContent());
            return this.messageRepository.save(message);
        }
        return null;
    }
}