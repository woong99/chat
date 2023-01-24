package com.example.woong99.stomp.repository;

import com.example.woong99.stomp.entity.PublicChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicChatRoomRepository extends JpaRepository<PublicChatRoom, String> {
}
