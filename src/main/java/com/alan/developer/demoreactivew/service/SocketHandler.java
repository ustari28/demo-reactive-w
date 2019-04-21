package com.alan.developer.demoreactivew.service;

import com.alan.developer.demoreactivew.model.EventVue;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Log
public class SocketHandler extends TextWebSocketHandler {

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("ID:" + session.getId());
        log.info("URI:" +session.getUri());
        //for(WebSocketSession webSocketSession : sessions) {
            EventVue value = new ObjectMapper().readValue(message.asBytes(), EventVue.class);
            session.sendMessage(new TextMessage("Hello " + value.getTitle() + " !"));
        //}
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connection is ready");
        sessions.add(session);
    }
}
