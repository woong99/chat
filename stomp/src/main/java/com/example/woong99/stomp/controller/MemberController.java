package com.example.woong99.stomp.controller;

import com.example.woong99.stomp.annotation.AuthUser;
import com.example.woong99.stomp.dto.LoginRequestDto;
import com.example.woong99.stomp.dto.MemberResponseDto;
import com.example.woong99.stomp.dto.SignUpRequestDto;
import com.example.woong99.stomp.dto.TokenDto;
import com.example.woong99.stomp.entity.Member;
import com.example.woong99.stomp.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        TokenDto tokenDto = memberService.login(loginRequestDto);
        log.info("tokenDto : {}", tokenDto);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        memberService.signUp(signUpRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyUserInfo(@AuthUser Member member) {
        log.info("member : {}", member);
        return ResponseEntity.ok(MemberResponseDto.of(member));
    }
}
