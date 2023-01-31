package com.example.woong99.stomp.entity;

import com.example.woong99.stomp.common.Authority;
import jakarta.persistence.*;
import lombok.*;

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
}
