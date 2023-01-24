package com.example.woong99.stomp.adapter;

import com.example.woong99.stomp.entity.Member;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
public class UserAdapter extends User {
    private Member member;

    public UserAdapter(Member member) {
        super(member.getEmail(), member.getPassword(),
                List.of(new SimpleGrantedAuthority(member.getAuthority().toString())));
        this.member = member;
    }
}
