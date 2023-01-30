package com.example.woong99.stomp.config;

import com.example.woong99.stomp.security.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Slf4j
public class FilterChannelInterceptor implements ChannelInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        assert headerAccessor != null;

        if (!StompCommand.UNSUBSCRIBE.equals(headerAccessor.getCommand()) && !StompCommand.DISCONNECT.equals(headerAccessor.getCommand())) {
            String authorizationHeader = String.valueOf(headerAccessor.getNativeHeader("Authorization"));
            if (authorizationHeader == null || authorizationHeader.equals("null")) {
                throw new MessageDeliveryException("로그인 후 이용해주세요.");
            }

            String token = Objects.requireNonNull(headerAccessor.getNativeHeader("Authorization")).get(0)
                    .replace("Bearer ", "");
            try {
                jwtTokenProvider.validateToken(token);
                Authentication user = jwtTokenProvider.getAuthentication(token);
                headerAccessor.setUser(user);
            } catch (MessageDeliveryException e) {
                throw new MessageDeliveryException("메세지 에러");
            } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                     IllegalArgumentException e) {
                throw new MessageDeliveryException("인증 정보가 올바르지 않습니다. 다시 로그인 후 이용해주세요.");
            }
        }

        return message;
    }
}
