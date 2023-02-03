# STOMP

## 📝 프로젝트 설명

WebSocket의 단점을 개선하기 위해 STOMP를 이용하여 사용자 인증, 여러 개의 채팅방 구독, 1대1 개인 채팅 등의 여러 기능을 구현한 프로젝트이다.

---

## 🎁 기능

### ✅ 로그인 및 회원가입

<table>
<tr>
<td><img src="https://user-images.githubusercontent.com/76946536/216498100-c968c060-9242-4074-989c-d043b571b2f3.png" height="400" alt="로그인화면"/></td>
<td><img src="https://user-images.githubusercontent.com/76946536/216498458-aaca78a5-dfef-4060-9cc7-b390ab27c7d7.png" height="400" alt="회원가입화면" /></td>
</tr>
</table>

- JWT 토큰 방식으로 로그인이 이루어진다.

### ✅ 메인페이지

<table>
<tr>
<td>
<img src="https://user-images.githubusercontent.com/76946536/216498632-a24e60ec-2372-4c64-be35-c020db4b4c85.png" alt="메인페이지" height="400" />
</td>
</tr>
</table>

### ✅ 공개채팅방 목록 페이지

<table>
<tr>
<td><img src="https://user-images.githubusercontent.com/76946536/216513964-d0bec5b9-b798-4183-9751-73f0a39cd4c9.png" alt="공개채팅방 목록 페이지1" /></td>
<td><img src="https://user-images.githubusercontent.com/76946536/216513969-6d6bde31-f2fa-4ac1-811d-89b7e3c0b493.png" alt="공개채팅방 목록 페이지2" /></td>
<td><img src="https://user-images.githubusercontent.com/76946536/216513971-7482d5cb-4958-46d4-a316-eb73cbc99a5b.png" alt="공개채팅방 목록 페이지3" /></td>
</tr>
</table>

- 채팅방 이름을 설정하여 방을 생성할 수 있다.
- 채팅방에 구독중이면 채팅방 목록 페이지에서 `구독중`이라고 표시된다.
- 구독중인 채팅방에 메시지가 들어오면 숫자로 표시된다. (읽지 않은 메시지)

### ✅ 공개채팅방 채팅 페이지

<table>
<tr>
<td><img src="https://user-images.githubusercontent.com/76946536/216514547-2efe771e-0526-43ee-8357-fe5309bd63ed.png" alt="공개채팅방 채팅 페이지1"/></td>
<td><img src="https://user-images.githubusercontent.com/76946536/216514541-4dc9b7c4-ea07-407d-9d61-eff105c0dfc2.png" alt="공개채팅방 채팅 페이지2"/></td>
<td><img src="https://user-images.githubusercontent.com/76946536/216514539-eb19d809-360c-47ee-8ece-d5c5386a1847.png" alt="공개채팅방 채팅 페이지3"/></td>
</tr>
</table>

- 채팅방에 처음 입장하면 참여 메시지를 보낸다.
- 단체 메세지도 가능하다.
- 뒤로가기를 누르면 구독중인 채팅방이 유지가 되고, 채팅방 나가기를 누르면 구독을 끊는다.

### ✅ 개인채팅방 목록 페이지

<table>
<tr>
<td>
<img src="https://user-images.githubusercontent.com/76946536/216525527-c3a3b472-e014-4402-aebb-bdb0dc96be4c.png" alt="개인채팅방 목록 페이지1"/></td>
<td><img src="https://user-images.githubusercontent.com/76946536/216525531-1fc9e922-6a4d-44b0-a274-86378c1ef796.png" alt="개인채팅방 목록 페이지2"/></td></tr></table>

- 모든 유저의 닉네임 정보가 나온다.
- 채팅 버튼을 클릭하면 해당 유저와의 1대1 개인 채팅방으로 이동한다.
- 현재 접속중인 유저의 닉네임 옆에는 `접속중`이라고 표시된다.

### ✅ 개인채팅방 채팅 페이지

<table>
<tr>
<td><img src="https://user-images.githubusercontent.com/76946536/216524909-36c4f902-4567-4e81-a7c3-3ecc2f0cc159.png" alt="개인채팅방 채팅 페이지1"/></td>
<td><img src="https://user-images.githubusercontent.com/76946536/216524913-0c30371e-57a2-4e76-a3df-dddb6ff64e64.png" alt="개인채팅방 채팅 페이지2"/></td>
</tr>
</table>

- 공개채팅방과 동일한 형식으로 채팅이 진행된다.
- 상대방이 읽지 않았으면 메시지 옆에 1이라고 표시가 된다.

---

## 📗 학습 내용 및 코드 분석

### ⭐ STOMP

### ⭐ build.gradle

```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.0.1'
    id 'io.spring.dependency-management' version '1.1.0'
    // querysl
    id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"
}

group = 'com.example.woong99'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '17'

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'mysql:mysql-connector-java'
    implementation 'org.springframework.boot:spring-boot-starter-websocket'
    // ====================stomp======================
    implementation group: 'org.webjars', name: 'stomp-websocket', version: '2.3.3-1'
    // ====================stomp======================
    // ====================jwt========================
    implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
    implementation 'io.jsonwebtoken:jjwt-impl:0.11.5'
    implementation 'io.jsonwebtoken:jjwt-jackson:0.11.5'
    // ====================jwt========================
    // ==================querydsl=====================
    implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
    annotationProcessor "com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jakarta"
    annotationProcessor "jakarta.annotation:jakarta.annotation-api"
    annotationProcessor "jakarta.persistence:jakarta.persistence-api"
    // ==================querydsl=====================
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    runtimeOnly 'com.h2database:h2'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
    testCompileOnly 'org.projectlombok:lombok'
    testAnnotationProcessor 'org.projectlombok:lombok'
}

tasks.named('test') {
    useJUnitPlatform()
}

// ========================querydsl=======================
def querydslDir = "$buildDir/generated/querydsl"

querydsl {
    jpa = true
    querydslSourcesDir = querydslDir
}

sourceSets {
    main.java.srcDir querydslDir
}

configurations {
    querydsl.extendsFrom compileClasspath
}

compileQuerydsl {
    options.annotationProcessorPath = configurations.querydsl
}

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
    querydsl.extendsFrom compileClasspath
}
// ========================querydsl=======================
```

---

## 참고자료

- https://velog.io/@jkijki12/%EC%B1%84%ED%8C%85-STOMP-JWT
