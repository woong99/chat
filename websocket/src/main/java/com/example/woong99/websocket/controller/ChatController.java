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
        if (nickname == null || nickname.equals("")) {
            return "index";
        }
        return "chat";
    }

    @RequestMapping("/login")
    public String login(UserRequestDto userRequestDto, HttpServletRequest request) {
        request.getSession().setAttribute("nickname", userRequestDto.getNickname());
        return "redirect:/chat";
    }
}
