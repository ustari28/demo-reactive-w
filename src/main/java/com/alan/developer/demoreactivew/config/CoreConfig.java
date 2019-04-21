package com.alan.developer.demoreactivew.config;

import com.alan.developer.demoreactivew.service.SocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;

@Configuration
public class CoreConfig {

    @Bean
    public Scheduler scheduler() {
        return reactor.core.scheduler.Schedulers.newParallel("my-paralallel", 500);
    }
    @Bean
    public SocketHandler socketHandler() {
        return new SocketHandler();
    }
}
