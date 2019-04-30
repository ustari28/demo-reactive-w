package com.alan.developer.demoreactivew.service;

import com.alan.developer.demoreactivew.model.TaskVue;
import lombok.extern.java.Log;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Log
public class ProgressScheduledTask {

    final Map<String, TaskVue> tasks = new ConcurrentHashMap();
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Scheduled(fixedRate = 10000)
    public void progressTask() {
        log.info("Processing....");
        final Map<String, TaskVue> temporary = new ConcurrentHashMap();
        tasks.forEach((k, t) -> {
            Integer progress = RandomUtils.nextInt(t.getProgress(), 101);
            TaskVue newTask = TaskVue.builder()
                    .owner(t.getOwner())
                    .uuid(t.getUuid())
                    .title(t.getTitle())
                    .description(t.getDescription())
                    .start(t.getStart())
                    .end(t.getEnd())
                    .duration(t.getDuration())
                    .progress(progress)
                    .build();
            if (progress < 100) {
                temporary.put(k, newTask);
            } else {
                newTask.setEnd(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
            SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
            accessor.setSessionId(newTask.getOwner());
            MessageHeaders headers = accessor.getMessageHeaders();
            messagingTemplate.convertAndSendToUser(newTask.getOwner(), "/queue/tasks", newTask, headers);
        });
        tasks.clear();
        tasks.putAll(temporary);

    }

    public void addTask(TaskVue task) {
        tasks.put(task.getUuid(), task);
    }
}
