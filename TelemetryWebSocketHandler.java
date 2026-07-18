package com.heapvortex.backend.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heapvortex.backend.dto.response.GraphDTO;
import com.heapvortex.backend.dto.response.LiveTelemetryDTO;
import com.heapvortex.backend.jmx.JmxTelemetryService;
import com.heapvortex.backend.service.HeapAnalysisService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TelemetryWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JmxTelemetryService jmxTelemetryService;
    private final HeapAnalysisService heapAnalysisService;

    private int tickCounter = 0;

    public TelemetryWebSocketHandler(JmxTelemetryService jmxTelemetryService,
                                      HeapAnalysisService heapAnalysisService) {
        this.jmxTelemetryService = jmxTelemetryService;
        this.heapAnalysisService = heapAnalysisService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(session.getId(), session);
        sendSafely(session, wrap("GRAPH", heapAnalysisService.analyzeHeap()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getId());
    }

    /** Broadcasts fresh telemetry to every connected client once per second. */
    @Scheduled(fixedRateString = "${heapvortex.telemetry.interval-ms:1000}")
    public void broadcastTelemetry() {
        if (sessions.isEmpty()) return;

        LiveTelemetryDTO telemetry = jmxTelemetryService.getTelemetry();
        broadcast(wrap("TELEMETRY", telemetry));

        // Refresh the heap graph every 5 ticks so the 3D view updates
        // without re-computing an expensive graph on every single frame.
        tickCounter++;
        if (tickCounter % 5 == 0) {
            GraphDTO graph = heapAnalysisService.analyzeHeap();
            broadcast(wrap("GRAPH", graph));
        }
    }

    private void broadcast(String payload) {
        for (WebSocketSession session : sessions.values()) {
            sendSafely(session, payload);
        }
    }

    private void sendSafely(WebSocketSession session, String payload) {
        try {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(payload));
            }
        } catch (Exception e) {
            sessions.remove(session.getId());
        }
    }

    private String wrap(String type, Object data) {
        try {
            return objectMapper.writeValueAsString(new Envelope(type, data));
        } catch (Exception e) {
            return "{\"type\":\"ERROR\",\"data\":null}";
        }
    }

    private record Envelope(String type, Object data) {}
}
