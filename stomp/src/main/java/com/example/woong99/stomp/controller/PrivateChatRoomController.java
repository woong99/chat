package com.example.woong99.stomp.controller;

import com.example.woong99.stomp.dto.PrivateChatRoomRequestDto;
import com.example.woong99.stomp.dto.PrivateChatRoomResponseDto;
import com.example.woong99.stomp.service.PrivateChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private-room")
public class PrivateChatRoomController {
    private final PrivateChatRoomService privateChatRoomService;

    @PostMapping
    public ResponseEntity<PrivateChatRoomResponseDto> getPrivateChatMessageList(@RequestBody PrivateChatRoomRequestDto privateChatRoomRequestDto) {
        return ResponseEntity.ok(privateChatRoomService.getPrivateChatRoom(privateChatRoomRequestDto));
    }
}
