package com.example.woong99.stomp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity(name = "stomp_private_chat_room")
public class PrivateChatRoom {
    @Id
    @Column(nullable = false, name = "stomp_private_chat_room_id")
    private String id;
}
