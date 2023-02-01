package com.example.woong99.stomp.service;

import com.example.woong99.stomp.dto.ChatMessageDto;
import com.example.woong99.stomp.entity.ChatMessage;
import com.example.woong99.stomp.entity.PrivateChatRoom;
import com.example.woong99.stomp.repository.ChatMessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public void saveMessage(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    @Transactional
    public void updateIsRead(String sender, PrivateChatRoom privateChatRoom) {
        chatMessageRepository.updateIsEnterBySenderAndRoomId(sender, privateChatRoom);
    }

    public List<ChatMessageDto> getMessageList(String roomId) {
        return chatMessageRepository.findAllByPrivateChatRoom_Id(roomId).stream().map(ChatMessageDto::of).collect(Collectors.toList());
    }
}
