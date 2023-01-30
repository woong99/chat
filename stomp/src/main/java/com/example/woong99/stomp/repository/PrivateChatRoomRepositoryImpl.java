package com.example.woong99.stomp.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PrivateChatRoomRepositoryImpl implements PrivateChatRoomRepositoryCustom {
    private final JPAQueryFactory queryFactory;
}
