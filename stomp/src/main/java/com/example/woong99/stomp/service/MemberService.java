package com.example.woong99.stomp.service;

import com.example.woong99.stomp.dto.LoginRequestDto;
import com.example.woong99.stomp.dto.MemberResponseDto;
import com.example.woong99.stomp.dto.SignUpRequestDto;
import com.example.woong99.stomp.dto.TokenDto;
import com.example.woong99.stomp.entity.Member;
import com.example.woong99.stomp.repository.MemberRepository;
import com.example.woong99.stomp.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public TokenDto login(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequestDto.getEmail(), loginRequestDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("authentication : {}", authentication);
        return jwtTokenProvider.generateTokenDto(authentication);
    }

    public void signUp(SignUpRequestDto signUpRequestDto) {
        if (memberRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        Member member = signUpRequestDto.toMember(passwordEncoder);
        log.info("member : {}", member);
        memberRepository.save(member);
    }

    public List<MemberResponseDto> getAllMembers() {
        return memberRepository.findAll().stream().map(MemberResponseDto::ofNickname).collect(Collectors.toList());
    }
}
