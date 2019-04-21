package com.alan.developer.demoreactivew.config;

import com.alan.developer.demoreactivew.service.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private SocketHandler socketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, "/sockejs").setAllowedOrigins("*").withSockJS();
    }

}

/**
 * @Configuration
 * @EnableWebSocketMessageBroker public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
 * @Override public void configureMessageBroker(MessageBrokerRegistry config) {
 * config.enableSimpleBroker("/topic");
 * config.setApplicationDestinationPrefixes("/app");
 * }
 * @Override public void registerStompEndpoints(StompEndpointRegistry registry) {
 * // Point of connection for frameworks
 * registry.addEndpoint("/gs-guide-websocket").setAllowedOrigins("*").withSockJS().setWebSocketEnabled(true);
 * }
 * }
 */
