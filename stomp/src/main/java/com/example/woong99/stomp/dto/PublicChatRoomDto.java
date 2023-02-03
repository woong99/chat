package com.example.woong99.stomp.dto;

import com.example.woong99.stomp.entity.PublicChatRoom;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@Slf4j
public class PublicChatRoomDto {
    private String roomId;
    private String name;

    public PublicChatRoomDto(String roomId, String name) {
        this.roomId = roomId == null ? UUID.randomUUID().toString() : roomId;
        this.name = name;
    }

    public static PublicChatRoomDto from(PublicChatRoom entity) {
        return new PublicChatRoomDto(entity.getId(), entity.getName());
    }

    public PublicChatRoom toEntity(PublicChatRoomDto publicChatRoomDto) {
        return PublicChatRoom.builder()
                .id(publicChatRoomDto.getRoomId() == null ? UUID.randomUUID().toString() : publicChatRoomDto.getRoomId())
                .name(publicChatRoomDto.getName())
                .build();
    }
}
