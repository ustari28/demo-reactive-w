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
            if (t.getProgress() < 100) {
                TaskVue newTask = TaskVue.builder()
                        .owner(t.getOwner())
                        .uuid(t.getUuid())
                        .title(t.getTitle())
                        .description(t.getDescription())
                        .start(t.getStart())
                        .end(t.getEnd())
                        .duration(t.getDuration())
                        .progress(RandomUtils.nextInt(t.getProgress(), 101))
                        .build();
                temporary.put(k, newTask);
                SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
                accessor.setSessionId(newTask.getOwner());
                MessageHeaders headers = accessor.getMessageHeaders();

                log.info("sending to ->" + newTask.getOwner());
                messagingTemplate.convertAndSendToUser(newTask.getOwner(), "/queue/tasks", newTask, headers);
            }
        });
        tasks.putAll(temporary);

    }

    public void addTask(TaskVue task) {
        tasks.put(task.getUuid(), task);
    }
}
