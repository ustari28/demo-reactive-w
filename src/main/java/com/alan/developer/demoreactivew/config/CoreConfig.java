package com.alan.developer.demoreactivew.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.scheduler.Scheduler;

@Configuration
public class CoreConfig {

    @Bean
    public Scheduler scheduler() {
        return reactor.core.scheduler.Schedulers.newParallel("my-paralallel", 500);
    }

    /**
    @Order(0)
    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter() {
        return new JWTAuthorizationFilter();
    }
    */
}
