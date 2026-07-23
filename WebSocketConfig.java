package com.heapvortex.config;

import com.heapvortex.websocket.TelemetryWebSocketHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final TelemetryWebSocketHandler handler;
    private final String origins;
    public WebSocketConfig(TelemetryWebSocketHandler handler, @Value("${heapvortex.cors.allowed-origins:http://localhost:5173}") String origins) { this.handler = handler; this.origins = origins; }
    @Override public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler, "/websocket").setAllowedOriginPatterns(origins.split(","));
    }
}
