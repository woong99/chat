package com.example.woong99.stomp.controller;

import com.example.woong99.stomp.dto.ChatMessageDto;
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
        message.setWriter(principal.getName());
        template.convertAndSendToUser(message.getReceiver(), "/sub/private", message);
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
