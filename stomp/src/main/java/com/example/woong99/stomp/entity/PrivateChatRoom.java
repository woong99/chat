package com.example.woong99.stomp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.List;

@Getter
@Entity(name = "stomp-private-chat-room")
public class PrivateChatRoom {
    @Id
    @Column(nullable = false, name = "stomp-private-chat-room-id")
    private String id;

    //    @Column(nullable = false, name="stomp-private-chat-room")
    @OneToMany
    private List<Member> memberList;
}
