package com.example.woong99.stomp.repository;

import com.example.woong99.stomp.entity.PrivateChatRoomConnectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateChatRoomConnectMemberRepository extends JpaRepository<PrivateChatRoomConnectMember, Long>, PrivateChatRoomConnectMemberRepositoryCustom {
}
