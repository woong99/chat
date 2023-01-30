package com.example.woong99.stomp.repository;

import com.example.woong99.stomp.entity.PrivateChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateChatRoomRepository extends JpaRepository<PrivateChatRoom, Long>,
        PrivateChatRoomRepositoryCustom {
}
