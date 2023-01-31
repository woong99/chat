package com.example.woong99.stomp.repository;

import com.example.woong99.stomp.dto.PrivateChatRoomRequestDto;
import com.example.woong99.stomp.entity.PrivateChatRoomConnectMember;

public interface PrivateChatRoomConnectMemberRepositoryCustom {
    PrivateChatRoomConnectMember findPrivateChatRoomByUsers(PrivateChatRoomRequestDto privateChatRoomRequestDto);
}
