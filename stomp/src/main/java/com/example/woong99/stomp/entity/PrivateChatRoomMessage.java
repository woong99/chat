package com.example.woong99.stomp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity(name = "stomp_private_chat_room_message")
@Getter
@ToString
public class PrivateChatRoomMessage {
    @Id
    @Column(nullable = false, name = "stomp_private_chat_room_message_room_id")
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "stomp_member")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "stomp_private_chat_room")
    private PrivateChatRoom privateChatRoom;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false, name = "stomp_private_chat_room_message_send_time")
    private LocalDateTime sendTime;

    @Column(nullable = false, updatable = false, name = "stomp_private_chat_room_message_message")
    private String message;
}
