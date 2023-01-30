package com.example.woong99.stomp.dto;

import lombok.Data;

@Data
public class ChatMessageDto {
    private String roomId;
    private String writer;
    private String receiver;
    private String message;
}
