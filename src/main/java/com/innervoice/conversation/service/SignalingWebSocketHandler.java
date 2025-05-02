package com.innervoice.conversation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innervoice.conversation.domain.SignalingMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class SignalingWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    private final Map<Long, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("WebSocket connected: sessionId={} URI={} ", session.getId(), session.getUri());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Received WebSocket message from session {}: {}", session.getId(), message.getPayload());
        SignalingMessage msg = objectMapper.readValue(message.getPayload(), SignalingMessage.class);
        Long roomId = msg.getRoomId();
        log.info("Parsed SignalingMessage: type={} roomId={}", msg.getType(), roomId);

        rooms.computeIfAbsent(roomId, id -> ConcurrentHashMap.newKeySet());

        switch (msg.getType()) {
            case "join" -> {
                rooms.get(roomId).add(session);
                log.info("Session {} joined room {}", session.getId(), roomId);
                // 새 피어 입장 알림
                SignalingMessage notify = new SignalingMessage();
                notify.setType("new_peer");
                notify.setRoomId(roomId);

                String notifyPayload = objectMapper.writeValueAsString(notify);
                for (WebSocketSession peer : rooms.get(roomId)) {
                    if (!peer.equals(session) && peer.isOpen()) {
                        peer.sendMessage(new TextMessage(notifyPayload));
                        log.info("Notified peer session {} of new_peer in room {}", peer.getId(), roomId);
                    }
                }
            }
            case "offer", "answer", "candidate" -> {
                log.info("Relaying {} in room {} from session {}", msg.getType(), roomId, session.getId());
                for (WebSocketSession peer : rooms.get(roomId)) {
                    if (!peer.equals(session) && peer.isOpen()) {
                        peer.sendMessage(new TextMessage(message.getPayload()));
                        log.debug("Forwarded payload to session {}: {}", peer.getId(), message.getPayload());
                    }
                }
            }
            case "leave" -> {
                log.info("Session {} leaving room {}", session.getId(), roomId);
                leaveRoom(roomId, session);
            }
            default -> log.warn("Unknown message type received: {} from session {}", msg.getType(), session.getId());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("WebSocket closed: sessionId={} closeStatus={}", session.getId(), status);
        rooms.keySet().forEach(roomId -> leaveRoom(roomId, session));
    }

    private void leaveRoom(Long roomId, WebSocketSession session) {
        Set<WebSocketSession> sessions = rooms.get(roomId);
        if (sessions != null) {
            sessions.remove(session);
            log.debug("Removed session {} from room {}", session.getId(), roomId);
            if (sessions.isEmpty()) {
                rooms.remove(roomId);
                log.debug("Deleted empty room {}", roomId);
            }
        }
    }
}