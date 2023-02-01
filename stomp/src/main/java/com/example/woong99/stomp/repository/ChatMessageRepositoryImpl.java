package com.example.woong99.stomp.repository;

import com.example.woong99.stomp.entity.PrivateChatRoom;
import com.example.woong99.stomp.entity.QChatMessage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    QChatMessage chatMessage = QChatMessage.chatMessage;

    @Override
    public void updateIsEnterBySenderAndRoomId(String sender, PrivateChatRoom privateChatRoom) {
        jpaQueryFactory
                .update(chatMessage)
                .set(chatMessage.isRead, "Y")
                .where(chatMessage.privateChatRoom.eq(privateChatRoom).and(chatMessage.sender.eq(sender)))
                .execute();
    }
}
