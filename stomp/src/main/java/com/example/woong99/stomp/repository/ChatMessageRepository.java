package com.example.woong99.stomp.repository;

import com.example.woong99.stomp.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>, ChatMessageRepositoryCustom {
    List<ChatMessage> findAllByPrivateChatRoom_Id(String roomId);

}
