# Websocket

### 프로젝트 설명

웹소켓을 사용한 간단한 채팅 프로그램이다.

---

### 프로젝트 기획 의도

현재 인턴을 하는 회사에서 `jsp`를 이용한다. 하지만 나는 그동안 리액트를 이용해 프론트엔드를 구축해 개발을 진행해왔다. 이번 기회를 통해 프론트를 별도로 분리하지 않고 개발하는 법에 대해 공부해 보기로
했다. `jsp`를 이용해 구축하려 했지만, `springboot`에서는 `thymeleaf` 템플릿 엔진을 권장하기 때문에 `thymeleaf`를 이용해 뷰를 구현하기로
했다. `Websocket` -> `SockJS` -> `STOMP` -> `RabbitMQ` 순으로 진행할 예정이다. 디자인이나 채팅 외의 기능은 최소한으로 진행해 채팅 기능을 공부하는 데에만 집중할
예정이다. `RabbitMQ`까지 공부를 완료하면 로그인 기능을 추가해 사용자를 검증하고, 데이터베이스를 연결해 실제 메신저 어플처럼 구현해 볼 예정이다.

---

### 기능

#### 로그인 화면

닉네임을 입력받아 `request.getSession()`에 저장해 채팅 뷰로 전달한다.

<img src="https://user-images.githubusercontent.com/76946536/213326473-d7f3bd43-6055-43c3-9dad-e47932bac654.png" height="400" alt="로그인화면"/>

#### 1대1 채팅 화면

<img src="https://user-images.githubusercontent.com/76946536/213327016-7186b2f2-2080-406c-8018-58ce140549d5.png" height="400" alt="1대1 채팅화면"/>
<img src="https://user-images.githubusercontent.com/76946536/213327024-ab8ba448-6f00-441c-8d90-2b3cf0116fce.png" height="400" alt="1대1 채팅화면"/>

#### 단체 채팅 화면

<img src="https://user-images.githubusercontent.com/76946536/213327593-e0f07d17-b2b6-4e85-848a-b4020e065f43.png" height="400" alt="1대1 채팅화면"/>
<img src="https://user-images.githubusercontent.com/76946536/213327599-f9aef4d7-7e94-4373-9c95-62a8851a2083.png" height="400" alt="1대1 채팅화면"/>
<img src="https://user-images.githubusercontent.com/76946536/213327601-aeb7515b-53c8-42c5-9a10-bffd5fd15704.png" height="400" alt="1대1 채팅화면"/>

---

## 학습 내용 및 코드 분석

- [HttpSession](https://github.com/woong99/Note/blob/master/Spring%2BSpringBoot/HttpSession.md)
- [WebSocket](https://github.com/woong99/Note/blob/master/Spring%2BSpringBoot/WebSocket.md)

### 라이브러리 추가

```gradle
// build.gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf:3.0.1'
    implementation 'nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.1.0'

    ///////////////////////////////////////////////////////////////////////
    implementation 'org.springframework.boot:spring-boot-starter-websocket'
    ///////////////////////////////////////////////////////////////////////
    
    compileOnly 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

### WebSocket Handler

- 소켓통신은 **서버와 클라이언트가 1:N의 관계**를 맺는다. 즉, 하나의 서버에 다수 클라이언트가 접속할 수 있다.
    - 서버는 다수의 클라이언트가 보낸 메세지를 처리할 핸들러가 필요하다.
- 텍스트 기반의 채팅을 구현해볼 것이므로 `TextWebSocketHandler`를 상속받아서 작성한다.

```java
package com.example.woong99.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ChatHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> list = new ArrayList<>();

    // Client가 접속 시 호출되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);

        log.info("{} - 클라이언트 접속", session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : {}", payload);

        for (WebSocketSession sess : list) {
            sess.sendMessage(message);
        }
    }

    // Client가 접속 해제 시 호출되는 메서드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} - 클라이언트 접속 해제", session);
        list.remove(session);
    }
}
```

> ### payload
> - 페이로드란 전송되는 데이터를 의미한다.
> - 데이터를 전송할 때, Header와 메타 데이터, 에러 체크 비트 등과 같은 다양한 요소들을 함께 보내 데이터 전송 효율과 안정성을 높히게 된다.
> - 이때, 보내고자 하는 데이터 자체를 의미하는 것이 페이로드이다.

### WebSocket Config

- 핸들러를 이용해 WebSocket을 활성화하기 위한 Config를 작성한다.
- WebSocket에 접속하기 위한 Endpoint는 `/chat`으로 설정
- **도메인이 다른 서버에서도 접속 가능하도록 CORS: `setAllowedOrigins("*")`를 추가**
- 클라이언트가 `ws://localhost:8080/chat`으로 커넥션을 연결하고 메세지 통신을 할 수 있는 준비가 끝났다.

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
        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
    }
}
```

> ### Endpoint
> - 같은 URL들에 대해서도 다른 요청을 하게끔 구별하게 해주는 항목(GET, PUT, DELETE) 등의 메서드에 따라 다른 요청을 한다.
> <figure align="center">
> <img src="https://user-images.githubusercontent.com/76946536/213364514-2e82e3a7-8804-4956-b3e1-426625d9541c.png" alt="endpoint"/>
> <figcaption >출처: https://velog.io/@kho5420/Web-API-%EA%B7%B8%EB%A6%AC%EA%B3%A0-EndPoint</figcaption>
> </figure>

### ChatController

```java
package com.example.woong99.websocket.controller;

import com.example.woong99.websocket.dto.UserRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class ChatController {

    @RequestMapping("/chat")
    public String chatGET(HttpServletRequest request) {
        String nickname = (String) request.getSession().getAttribute("nickname");
        if (nickname == null || nickname.equals("")) {  // 닉네임이 없으면 로그인 페이지로 이동(유사 로그인 검증)
            return "index";
        }
        return "chat";
    }

    @RequestMapping("/login")
    public String login(UserRequestDto userRequestDto, HttpServletRequest request) {
        request.getSession().setAttribute("nickname", userRequestDto.getNickname()); // 세션에 사용자 닉네임 정보를 저장해 뷰에서 사용
        return "redirect:/chat"; // redirect를 사용하지 않으면 login?nickname=OOO과 같이 데이터가 쿼리 파라미터에 표시가 되서 redirect를 사용
    }
}

```

### JS

```javascript
<script th:inline="javascript">
    $(document).ready(function () {
    // 나가기 버튼 클릭 시 로그인 페이지로 이동(로그아웃)
    $("#disconnect").on("click", () => {
        location.href = "/";
    })

    $("#button-send").on("click", () => {
    send();
});

    const websocket = new WebSocket("ws://localhost:8080/ws/chat");

    websocket.onmessage = onMessage;
    websocket.onopen = onOpen;
    websocket.onclose = onClose;

    function send() {
    let msg = $("#msg");
    websocket.send("전달:" + [[${session.nickname}]] + ":" + msg.val());
    msg.val("");
}

    // 채팅방에서 나갔을 떄
    function onClose() {
    const str = "퇴장:" + [[${session.nickname}]] + ":님이 방을 나가셨습니다.";
    websocket.send(str);
}

    // 채팅방에 들어왔을 떄
    function onOpen() {
    const str = "입장:" + [[${session.nickname}]] + ":님이 입장하셨습니다.";
    websocket.send(str);
}

    function onMessage(msg) {
    let data = msg.data;
    let sessionId;
    let message;
    let type;
    const arr = data.split(":");
    type = arr[0];
    sessionId = arr[1];
    message = arr[2];

    if (type === "입장" || type === "퇴장") {
    let str = "<div class=\"mt-2\">";
    str += `<div>${sessionId} ${message}</div>`;
    str += "</div></div>";
    $("#message-area").append(str);
} else {
    if (sessionId === [[${session.nickname}]]) {
    let str = "<div class=\"mt-2 d-flex justify-content-end\">";
    str += "<div class=\"d-flex\">";
    str += "<div class=\"align-self-end\">12:11</div>";
    str += `<div class=\"bg-body-secondary px-1 py-1 rounded fs-5 ms-2\">${message}</div>`;
    str += "</div></div>";
    $("#message-area").append(str);
} else {
    let str = "<div class=\"mt-2\">";
    str += `<div>${sessionId}</div>`;
    str += "<div class=\"d-flex\">";
    str += `<div class=\"bg-body-secondary px-1 py-1 rounded fs-5\">${message}</div>`;
    str += "<div class=\"align-self-end ms-2\">12:12</div>";
    str += "</div></div>";
    $("#message-area").append(str);
}
}
}
})
</script>
```

---

## 결론

- 채팅방이 단 하나이다.
- 웹소켓을 지원하지 않는 브라우저에서는 동작하지 않을 것이다.
    - 모든 클라이언트의 브라우저에서 웹소켓을 지원한다는 보장이 없다.
    - Server/Client 중간에 위치한 Proxy가 Upgrade 헤더를 해석하지 못해 서버에 전달하지 못할 수 있다.
    - Server/Client 중간에 위치한 Proxy가 유휴 상태에서 도중에 Connection을 종료시킬 수도 있다.
- SockJS에 대해 공부하고 적용해 볼 예정!!
- 세션과 웹소켓에 대한 개념을 정리하면서 완벽히 이해할 수 있었다. 또한, Thymeleaf를 이용해 뷰를 구현하면서 `@Controller`를 사용해 볼 수 있었다.

---

## 참고자료

- https://dev-gorany.tistory.com/212