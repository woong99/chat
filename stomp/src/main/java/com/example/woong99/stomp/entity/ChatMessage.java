package com.example.woong99.stomp.entity;

import com.example.woong99.stomp.dto.ChatMessageDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "stomp_chat_message")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "stomp_chat_message_id")
    private Long id;

    @Column(nullable = false, updatable = false, name = "stomp_chat_message_message")
    private String message;

    @Column(nullable = false, updatable = false, name = "stomp_chat_message_sender")
    private String sender;

    @Column(nullable = false, name = "stomp_chat_message_is_read")
    private String isRead;

    @ManyToOne
    @JoinColumn(name = "stomp_private_chat_room_id")
    private PrivateChatRoom privateChatRoom;

    public static ChatMessage toEntity(ChatMessageDto chatMessageDto, PrivateChatRoom privateChatRoom) {
        return ChatMessage.builder()
                .message(chatMessageDto.getMessage())
                .sender(chatMessageDto.getWriter())
                .privateChatRoom(privateChatRoom)
                .isRead(chatMessageDto.getIsRead())
                .build();
    }
}
