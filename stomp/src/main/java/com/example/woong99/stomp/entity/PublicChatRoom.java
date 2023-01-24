package com.example.woong99.stomp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "stomp_public_chat_room")
public class PublicChatRoom {
    @Id
    @Column(nullable = false, name = "stomp_public_chat_room_id")
    private String id;

    @Column(nullable = false, name = "stomp_public_chat_room_name")
    private String name;
}
