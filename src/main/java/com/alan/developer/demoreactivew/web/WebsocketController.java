package com.alan.developer.demoreactivew.web;

import com.alan.developer.demoreactivew.model.EventVue;
import com.alan.developer.demoreactivew.service.SocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Log
@Controller
public class WebsocketController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public EventVue sendMessage(@Payload EventVue chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public EventVue addUser(@Payload EventVue chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        log.info("New message");
        headerAccessor.getSessionAttributes().put("username", "Alan");
        return chatMessage;
    }
}