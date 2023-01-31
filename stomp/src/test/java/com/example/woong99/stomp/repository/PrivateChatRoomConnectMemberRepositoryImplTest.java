package com.example.woong99.stomp.repository;

import com.example.woong99.stomp.common.Authority;
import com.example.woong99.stomp.config.QuerydslConfig;
import com.example.woong99.stomp.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(QuerydslConfig.class)
@Slf4j
class PrivateChatRoomConnectMemberRepositoryImplTest {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PrivateChatRoomConnectMemberRepository privateChatRoomConnectMemberRepository;

    @Autowired
    PrivateChatRoomRepository privateChatRoomRepository;

    QMember member = QMember.member;
    QPrivateChatRoomConnectMember privateChatRoomConnectMember = new QPrivateChatRoomConnectMember("q1");
    QPrivateChatRoomConnectMember privateChatRoomConnectMember3 = new QPrivateChatRoomConnectMember("q3");


    @BeforeEach
    void init() {
        Member member1 = Member.builder()
                .email("test1")
                .nickname("test1")
                .password("test1")
                .authority(Authority.ROLE_USER)
                .build();
        Member member2 = Member.builder()
                .email("test2")
                .nickname("test2")
                .password("test2")
                .authority(Authority.ROLE_USER)
                .build();
        Member member3 = Member.builder()
                .email("test3")
                .nickname("test3")
                .password("test3")
                .authority(Authority.ROLE_USER)
                .build();
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        PrivateChatRoom privateChatRoom = PrivateChatRoom.builder().id("1").build();
        PrivateChatRoom privateChatRoom2 = PrivateChatRoom.builder().id("2").build();
        privateChatRoomRepository.save(privateChatRoom);
        privateChatRoomRepository.save(privateChatRoom2);
        PrivateChatRoomConnectMember privateChatRoomConnectMember1 = PrivateChatRoomConnectMember.builder()
                .member(member1)
                .privateChatRoom(privateChatRoom)
                .build();
        PrivateChatRoomConnectMember privateChatRoomConnectMember2 = PrivateChatRoomConnectMember.builder()
                .member(member2)
                .privateChatRoom(privateChatRoom)
                .build();
        PrivateChatRoomConnectMember privateChatRoomConnectMember3 = PrivateChatRoomConnectMember.builder()
                .member(member2)
                .privateChatRoom(privateChatRoom2)
                .build();
        PrivateChatRoomConnectMember privateChatRoomConnectMember4 = PrivateChatRoomConnectMember.builder()
                .member(member3)
                .privateChatRoom(privateChatRoom2)
                .build();
        privateChatRoomConnectMemberRepository.save(privateChatRoomConnectMember1);
        privateChatRoomConnectMemberRepository.save(privateChatRoomConnectMember2);
        privateChatRoomConnectMemberRepository.save(privateChatRoomConnectMember3);
        privateChatRoomConnectMemberRepository.save(privateChatRoomConnectMember4);
    }

    @Test
    void test() {
        List<PrivateChatRoomConnectMember> p = privateChatRoomConnectMemberRepository.findAll();
        for (PrivateChatRoomConnectMember item : p) {
            log.info("{}", item);
        }
        List<PrivateChatRoomConnectMember> privateChatRoom = jpaQueryFactory
                .selectFrom(privateChatRoomConnectMember)
                .join(privateChatRoomConnectMember3)
                .on(privateChatRoomConnectMember.privateChatRoom.eq(privateChatRoomConnectMember3.privateChatRoom))
                .where(privateChatRoomConnectMember.member.nickname.eq("test1").and(privateChatRoomConnectMember3.member.nickname.eq("test2")))
                .fetch();
        for (PrivateChatRoomConnectMember item : privateChatRoom) {
            log.info("{}", item);
        }
    }

}