package com.example.woong99.stomp.repository;

import com.example.woong99.stomp.entity.PrivateChatRoom;

public interface ChatMessageRepositoryCustom {
    void updateIsEnterBySenderAndRoomId(String sender, PrivateChatRoom privateChatRoom);
}
