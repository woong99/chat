package com.example.woong99.stomp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "stomp_private_chat_room")
public class PrivateChatRoom {
    @Id
    @Column(nullable = false, name = "stomp_private_chat_room_id")
    private String id;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "privateChatRoom")
    @JsonIgnore
    private List<ChatMessage> messageList = new ArrayList<>();
}
