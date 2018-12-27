package com.becky.becky.config;

import com.becky.becky.messagehandler.WebSocketMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageHandler(), "/message").withSockJS();
        registry.addHandler(messageHandler(), "/ws").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler messageHandler() {
        return new WebSocketMessageHandler();
    }

}
