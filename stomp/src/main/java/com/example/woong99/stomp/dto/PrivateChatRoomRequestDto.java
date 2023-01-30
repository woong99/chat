package com.example.woong99.stomp.dto;

import java.util.List;
import lombok.Data;

@Data
public class PrivateChatRoomRequestDto {
    private List<String> members;
}
