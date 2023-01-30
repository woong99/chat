package com.example.woong99.stomp.entity;

import com.example.woong99.stomp.common.Authority;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "stomp_member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "stomp_member_id")
    private Long id;

    @Column(nullable = false, name = "stomp_member_email")
    @Setter
    private String email;

    @Column(nullable = false, name = "stomp_member_password")
    @Setter
    private String password;

    @Column(nullable = false, name = "stomp_member_nickname")
    @Setter
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "stomp_member_authority")
    private Authority authority;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<PrivateChatRoomMessage> privateChatRoomMessageList = new ArrayList<>();
}
