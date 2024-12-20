package com.hari.gatherspace.config;


import com.hari.gatherspace.controller.ws.SpaceWsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SpaceWsController spaceController;

    @Autowired
    public WebSocketConfig(SpaceWsController spaceController) {
        this.spaceController = spaceController;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(spaceController, "/ws")
                .setAllowedOrigins("*"); // Configure allowed origins as needed
    }
}