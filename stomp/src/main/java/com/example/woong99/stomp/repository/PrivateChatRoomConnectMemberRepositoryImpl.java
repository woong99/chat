package com.example.woong99.stomp.repository;

import com.example.woong99.stomp.dto.PrivateChatRoomRequestDto;
import com.example.woong99.stomp.entity.PrivateChatRoomConnectMember;
import com.example.woong99.stomp.entity.QPrivateChatRoomConnectMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PrivateChatRoomConnectMemberRepositoryImpl implements PrivateChatRoomConnectMemberRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    QPrivateChatRoomConnectMember privateChatRoomConnectMember1 = new QPrivateChatRoomConnectMember("q1");
    QPrivateChatRoomConnectMember privateChatRoomConnectMember2 = new QPrivateChatRoomConnectMember("q3");

    @Override
    public PrivateChatRoomConnectMember findPrivateChatRoomByUsers(PrivateChatRoomRequestDto privateChatRoomRequestDto) {
        return jpaQueryFactory
                .selectFrom(privateChatRoomConnectMember2)
                .join(privateChatRoomConnectMember1)
                .on(privateChatRoomConnectMember1.privateChatRoom.eq(privateChatRoomConnectMember2.privateChatRoom))
                .where(privateChatRoomConnectMember1.member.nickname.eq(privateChatRoomRequestDto.getMemberOne())
                        .and(privateChatRoomConnectMember2.member.nickname.eq(privateChatRoomRequestDto.getMemberTwo())))
                .fetchOne();
    }
}
