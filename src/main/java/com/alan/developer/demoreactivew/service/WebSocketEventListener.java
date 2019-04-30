package com.alan.developer.demoreactivew.service;

import com.alan.developer.demoreactivew.model.EventVue;
import lombok.extern.java.Log;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * To manage e connect and disconnect events.
 */
@Log
@Component
public class WebSocketEventListener {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    /**
     * Lisnter listens new connection.
     *
     * @param event Properties of connection.
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection -> ");
        //TODO: send a message to inform about a new user (brodcast)
    }

    /**
     * Listener listens disconnect event.
     *
     * @param event Information about disconnected session.
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("User Disconnected : " + username);
            EventVue chatMessage = EventVue.builder().idx(RandomUtils.nextInt()).title("new user->" + username)
                    .date(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
