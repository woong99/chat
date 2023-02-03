package com.example.woong99.stomp.dto;

import com.example.woong99.stomp.entity.ChatMessage;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PrivateChatRoomResponseDto {
    private String roomId;
    private List<ChatMessage> messages;
    private String isEnter;
}
