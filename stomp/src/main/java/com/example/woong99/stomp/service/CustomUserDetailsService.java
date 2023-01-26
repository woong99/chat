package com.example.woong99.stomp.service;

import com.example.woong99.stomp.adapter.UserAdapter;
import com.example.woong99.stomp.entity.Member;
import com.example.woong99.stomp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberInfo) throws UsernameNotFoundException {
        Member member = memberInfo.contains("@")
                ? memberRepository.findByEmail(memberInfo)
                .orElseThrow(() -> new UsernameNotFoundException(memberInfo + " 을 DB에서 찾을 수 없습니다."))
                : memberRepository.findByNickname(memberInfo)
                .orElseThrow(() -> new UsernameNotFoundException(memberInfo + " 을 DB에서 찾을 수 없습니다."));
        return new UserAdapter(member);
    }
}
