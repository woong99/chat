package com.example.woong99.stomp.controller;

import com.example.woong99.stomp.dto.ChatMessageDto;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {
    private final SimpMessagingTemplate template;

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
}
