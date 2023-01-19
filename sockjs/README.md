# SockJS

## :pencil: 프로젝트 설명

이전 프로젝트의 문제점 중 브라우저 호환성을 해결하기 위해 SockJS를 추가하여 채팅을 구현하였다.

---

## :gift: 기능

### :white_check_mark: [WebSocket 프로젝트](https://github.com/woong99/chat/tree/master/websocket)와 동일

---

## :green_book: 학습 내용 및 코드 분석

### :star: 이전 프로젝트와의 차이점만 게시

### :star: [SockJS](https://github.com/woong99/Note/blob/master/Spring%2BSpringBoot/SockJS.md)

### :star: WebSocket Config

- Java Configuration을 통해 SockJS를 가능하게 한다.

```java
package com.example.woong99.websocket.config;

import com.example.woong99.websocket.controller.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket  // Websocket 활성화
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "/ws/chat")
                .setAllowedOrigins("http://localhost:8080") // 와일드 카드는 보안성에 안좋으므로, 직접 지정해준다. TODO : CORS에 대해서는 추후 공부해서 정리하기
                .withSockJS();  // 추가
    }
}
```

### :star: JS

- `WebSocket`을 `SockJS`로 바꾸어준다.

```javascript
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script> //sockJS 라이브러리 추가
const websocket = new WebSocket("ws://localhost:8080/ws/chat");
->
const websocket = new SockJS("ws/chat");
```

---

## :exclamation: 결론

- `SockJS`를 이용해 웹소켓을 지원하지 않는 브라우저에서 서버와 클라이언트 간 통신이 끊기지 않도록 구현했다!
- IE에서는 제대로 테스트해보지 못했지만, 참고자료를 통해 공부하다보니 호환성 문제가 있어 SockJS를 사용한다고 한다.
    - IE 지원이 종료되었다고 들었는데, 엣지에서는 IE 모드가 있다. IE 모드로 들어가보니 CSS도 엄청 깨지고, ES6 문법도 완벽히 지원하지 못하는것 같아 제대로 된 테스트를 해보지 못해 아쉽다.
- 다음 프로젝트에서는 `STOMP`를 이용해 여러 개의 채팅방을 구현해보자.

---

## :bell: 참고자료

- https://dev-gorany.tistory.com/224