package com.springsockets.playground.repositories;

import com.springsockets.playground.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IMessageRepository extends JpaRepository<Message, UUID> {
    @Query("SELECT m FROM Message m WHERE m.sender = :senderId AND m.receiver = :receiverId OR m.sender = :receiverId AND m.receiver = :senderId")
    List<Message> findMessagesByRoom(UUID senderId, UUID receiverId);
}
