package com.example.woong99.stomp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "stomp_private_chat_room_connect_member")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrivateChatRoomConnectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "stomp_private_chat_room_connect_member_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stomp_member")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "stomp_private_chat_room")
    private PrivateChatRoom privateChatRoom;
}
