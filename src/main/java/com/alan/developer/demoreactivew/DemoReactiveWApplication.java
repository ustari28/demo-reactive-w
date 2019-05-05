package com.alan.developer.demoreactivew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoReactiveWApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoReactiveWApplication.class, args);
    }
/**
 @Primary
 @Bean public WebClient webClient() {
 return WebClient.builder().clientConnector(new JettyClientHttpConnector()).build();
 }
 */
}

