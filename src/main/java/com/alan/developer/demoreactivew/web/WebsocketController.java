package com.alan.developer.demoreactivew.web;

import com.alan.developer.demoreactivew.model.EventVue;
import com.alan.developer.demoreactivew.service.SocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/ws/v1")
public class WebsocketController {

    //@Autowired
    //private SocketHandler socket;
    @Autowired
    private ObjectMapper mapper;

    @PostMapping("/send")
    public EventVue events(EventVue event) throws IOException {
        System.out.println("Entrando mensaje");
        event.setDate(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        //session.sendMessage(new TextMessage(mapper.writeValueAsString(event)));
        return event;
    }
}