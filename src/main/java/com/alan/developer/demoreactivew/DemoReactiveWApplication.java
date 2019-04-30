package com.alan.developer.demoreactivew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableScheduling
public class DemoReactiveWApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoReactiveWApplication.class, args);
    }

    @Primary
    @Bean
    public WebClient webClient() {
        return WebClient.builder().clientConnector(new JettyClientHttpConnector()).build();
    }

}

