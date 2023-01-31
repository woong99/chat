package com.example.woong99.stomp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "stomp_private_chat_room")
public class PrivateChatRoom {
    @Id
    @Column(nullable = false, name = "stomp_private_chat_room_id")
    private String id;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<ChatMessage> messageList = new ArrayList<>();
}
