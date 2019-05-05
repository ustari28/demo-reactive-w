package com.alan.developer.demoreactivew.web;

import com.alan.developer.demoreactivew.model.EventVue;
import com.alan.developer.demoreactivew.model.TaskVue;
import com.alan.developer.demoreactivew.service.ProgressScheduledTask;
import lombok.extern.java.Log;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Log
@Controller
public class WebsocketController {

    //@Autowired
    //private ProgressScheduledTask sheduler;

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


    @MessageMapping("/process/new")
    @SendToUser("/queue/tasks")
    public TaskVue newTask(@Payload TaskVue task, @Header("username") String username, SimpMessageHeaderAccessor headerAccessor) {
        log.info("Session ID->" + username);
        final LocalDateTime current = LocalDateTime.now();
        final Long duration = RandomUtils.nextLong(2, 15);
        final TaskVue newTask = TaskVue.builder()
                .owner(headerAccessor.getSessionId())
                .uuid(UUID.randomUUID().toString())
                .title(task.getTitle())
                .description(task.getDescription())
                .start(current.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                //.end(current.plusMinutes(duration).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .end(-1L)
                .duration(duration)
                .progress(1)
                .build();
        //sheduler.addTask(newTask);
        return newTask;
    }
}