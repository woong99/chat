package com.example.woong99.stomp.repository;

import com.example.woong99.stomp.entity.Member;
import com.example.woong99.stomp.entity.PrivateChatRoom;
import com.example.woong99.stomp.entity.PrivateChatRoomConnectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateChatRoomConnectMemberRepository extends JpaRepository<PrivateChatRoomConnectMember, Long>, PrivateChatRoomConnectMemberRepositoryCustom {
    //    PrivateChatRoomConnectMember findPrivateChatRoomConnectMemberByMember(Member member, String roomId);
    PrivateChatRoomConnectMember findPrivateChatRoomConnectMemberByMemberAndPrivateChatRoom(Member member, PrivateChatRoom privateChatRoom);
}
