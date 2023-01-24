package com.example.woong99.stomp.service;

import com.example.woong99.stomp.dto.PublicChatRoomDto;
import com.example.woong99.stomp.repository.PublicChatRoomRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicChatRoomService {
    private final PublicChatRoomRepository publicChatRoomRepository;

    public List<PublicChatRoomDto> getChatRooms() {
        return publicChatRoomRepository.findAll().stream().map(PublicChatRoomDto::from)
                .collect(Collectors.toList());
    }

    public void saveChatRoom(PublicChatRoomDto publicChatRoomDto) {
        log.info("publicChatRoomDto : {}", publicChatRoomDto);
        publicChatRoomRepository.save(publicChatRoomDto.toEntity(publicChatRoomDto));
    }

}
