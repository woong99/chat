package com.example.woong99.stomp.dto;

import com.example.woong99.stomp.entity.ChatMessage;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatMessageDto {
    private String roomId;
    private String writer;
    private String receiver;
    private String message;
    private String command;
    private String isRead;

    public static ChatMessageDto of(ChatMessage chatMessage) {
        return ChatMessageDto.builder()
                .roomId(chatMessage.getPrivateChatRoom().getId())
                .writer(chatMessage.getSender())
                .message(chatMessage.getMessage())
                .isRead(chatMessage.getIsRead())
                .build();
    }
}
