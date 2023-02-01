package com.example.woong99.stomp.controller;

import com.example.woong99.stomp.dto.ChatMessageDto;
import com.example.woong99.stomp.entity.ChatMessage;
import com.example.woong99.stomp.entity.PrivateChatRoom;
import com.example.woong99.stomp.repository.ChatMessageRepository;
import com.example.woong99.stomp.repository.MemberRepository;
import com.example.woong99.stomp.repository.PrivateChatRoomConnectMemberRepository;
import com.example.woong99.stomp.repository.PrivateChatRoomRepository;
import com.example.woong99.stomp.service.ChatMessageService;
import com.example.woong99.stomp.service.PrivateChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.security.Principal;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {
    private final SimpMessagingTemplate template;
    private final HashMap<String, String> simpSessionIdMap = new HashMap<>();
    private final String noticeDestination = "/sub/notice";
    private final PrivateChatRoomRepository privateChatRoomRepository;
    private final PrivateChatRoomConnectMemberRepository privateChatRoomConnectMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final PrivateChatRoomService privateChatRoomService;
    private final ChatMessageService chatMessageService;

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDto message, Principal principal) {
        log.info("입장 : {}", message);
        message.setWriter(principal.getName());
        message.setMessage(principal.getName() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDto message, Principal principal) {
        log.info("member : {}", principal.getName());
        message.setWriter(principal.getName());
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping("/private")
    public void privateMessage(ChatMessageDto message, Principal principal) {
        log.info("message : {}", message);
        message.setWriter(principal.getName());
        PrivateChatRoom privateChatRoom = privateChatRoomRepository.findAllById(message.getRoomId());
        ChatMessage chatMessage = ChatMessage.toEntity(message, privateChatRoom);

        if (message.getCommand().equals("ENTER")) {
            privateChatRoomService.updateIsEnter(message.getCommand(), principal.getName());
            chatMessageService.updateIsRead(message.getReceiver(), privateChatRoom);
            template.convertAndSendToUser(message.getReceiver(), "/sub/private", message);
        } else if (message.getCommand().equals("OUT")) {
            privateChatRoomService.updateIsEnter(message.getCommand(), principal.getName());
            template.convertAndSendToUser(message.getReceiver(), "/sub/private", message);
        } else {
            chatMessageRepository.save(chatMessage);
            template.convertAndSendToUser(message.getReceiver(), "/sub/private", message);
            template.convertAndSendToUser(message.getWriter(), "/sub/private", message);
        }
    }

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        String simpSessionId = (String) event.getMessage().getHeaders().get("simpSessionId");

        if (event.getUser() != null) {
            Principal user = event.getUser();
            if (user != null) {
                try {
                    String username = user.getName();
                    simpSessionIdMap.put(simpSessionId, username);
                } catch (Exception e) {
                    throw new MessageDeliveryException("인증 정보가 올바르지 않습니다. 다시 로그인 후 이용해주세요.");
                }
            }
        }
    }

    @EventListener
    public void handleSessionSubscribe(SessionSubscribeEvent event) {
        String destination = (String) event.getMessage().getHeaders().get("simpDestination");
        assert destination != null;
        if (destination.equals(noticeDestination)) {
            template.convertAndSend(noticeDestination, simpSessionIdMap.values());
        }
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        String simpSessionId = (String) event.getMessage().getHeaders().get("simpSessionId");
        simpSessionIdMap.remove(simpSessionId);
        template.convertAndSend(noticeDestination, simpSessionIdMap.values());
    }
}
