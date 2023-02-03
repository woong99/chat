package com.example.woong99.stomp.service;

import com.example.woong99.stomp.dto.PrivateChatRoomRequestDto;
import com.example.woong99.stomp.dto.PrivateChatRoomResponseDto;
import com.example.woong99.stomp.entity.ChatMessage;
import com.example.woong99.stomp.entity.Member;
import com.example.woong99.stomp.entity.PrivateChatRoom;
import com.example.woong99.stomp.entity.PrivateChatRoomConnectMember;
import com.example.woong99.stomp.repository.ChatMessageRepository;
import com.example.woong99.stomp.repository.MemberRepository;
import com.example.woong99.stomp.repository.PrivateChatRoomConnectMemberRepository;
import com.example.woong99.stomp.repository.PrivateChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateChatRoomService {
    private final PrivateChatRoomRepository privateChatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final PrivateChatRoomConnectMemberRepository privateChatRoomConnectMemberRepository;

    public PrivateChatRoomResponseDto getPrivateChatRoom(PrivateChatRoomRequestDto privateChatRoomRequestDto) {
        PrivateChatRoomConnectMember privateChatRoomConnectMember = privateChatRoomConnectMemberRepository.findPrivateChatRoomByUsers(privateChatRoomRequestDto);
        if (privateChatRoomConnectMember == null) {
            String roomId = UUID.randomUUID().toString();
            PrivateChatRoom privateChatRoom = PrivateChatRoom.builder().id(roomId).build();
            privateChatRoomRepository.save(privateChatRoom);
            Member memberOne = memberRepository.findByNickname(privateChatRoomRequestDto.getMemberOne()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
            Member memberTwo = memberRepository.findByNickname(privateChatRoomRequestDto.getMemberTwo()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
            PrivateChatRoomConnectMember newPrivateChatRoomConnectMember1 = PrivateChatRoomConnectMember.builder().member(memberOne).isEnter("OUT").privateChatRoom(privateChatRoom).build();
            PrivateChatRoomConnectMember newPrivateChatRoomConnectMember2 = PrivateChatRoomConnectMember.builder().member(memberTwo).isEnter("OUT").privateChatRoom(privateChatRoom).build();
            privateChatRoomConnectMemberRepository.save(newPrivateChatRoomConnectMember1);
            privateChatRoomConnectMemberRepository.save(newPrivateChatRoomConnectMember2);
            return PrivateChatRoomResponseDto.builder().roomId(roomId).build();
        } else {
            List<ChatMessage> messageList = privateChatRoomConnectMember.getPrivateChatRoom().getMessageList();
            log.info("messageList : {}", messageList);
            return PrivateChatRoomResponseDto.builder()
                    .roomId(privateChatRoomConnectMember.getPrivateChatRoom().getId())
                    .messages(messageList)
                    .isEnter(privateChatRoomConnectMember.getIsEnter())
                    .build();
        }
    }

    public void updateIsEnter(String command, String roomId, String nickname) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        PrivateChatRoom privateChatRoom = privateChatRoomRepository.findAllById(roomId);
        PrivateChatRoomConnectMember privateChatRoomConnectMember = privateChatRoomConnectMemberRepository.findPrivateChatRoomConnectMemberByMemberAndPrivateChatRoom(member, privateChatRoom);
        privateChatRoomConnectMember.setIsEnter(command);
        privateChatRoomConnectMemberRepository.save(privateChatRoomConnectMember);
    }
}
