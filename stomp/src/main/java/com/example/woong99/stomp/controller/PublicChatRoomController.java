package com.example.woong99.stomp.controller;

import com.example.woong99.stomp.dto.PublicChatRoomDto;
import com.example.woong99.stomp.service.PublicChatRoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public-room")
public class PublicChatRoomController {
    private final PublicChatRoomService publicChatRoomService;

    @GetMapping()
    public ResponseEntity<List<PublicChatRoomDto>> getRooms() {
        return ResponseEntity.ok(publicChatRoomService.getChatRooms());
    }

    @PostMapping()
    public ResponseEntity<Void> saveRoom(@RequestBody PublicChatRoomDto publicChatRoomDto) {
        publicChatRoomService.saveChatRoom(publicChatRoomDto);
        return ResponseEntity.ok().build();
    }
}
