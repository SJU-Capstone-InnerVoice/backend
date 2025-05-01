package com.innervoice.conversation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innervoice.conversation.domain.SignalingMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class SignalingWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    private final Map<Long, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        SignalingMessage msg = objectMapper.readValue(message.getPayload(), SignalingMessage.class);
        Long roomId = msg.getRoomId();
        rooms.computeIfAbsent(roomId, id -> ConcurrentHashMap.newKeySet());

        switch (msg.getType()) {
            case "join" -> {
                rooms.get(roomId).add(session);
                // 새 피어 입장 알림
                for (WebSocketSession peer : rooms.get(roomId)) {
                    if (!peer.equals(session) && peer.isOpen()) {
                        SignalingMessage notify = new SignalingMessage();
                        notify.setType("new_peer");
                        notify.setRoomId(roomId);
                        peer.sendMessage(new TextMessage(objectMapper.writeValueAsString(notify)));
                    }
                }
            }
            case "offer", "answer", "candidate" -> {
                // offer/answer/candidate 중계
                for (WebSocketSession peer : rooms.get(roomId)) {
                    if (!peer.equals(session) && peer.isOpen()) {
                        peer.sendMessage(new TextMessage(message.getPayload()));
                    }
                }
            }
            case "leave" -> leaveRoom(roomId, session);
            default -> System.err.println("Unknown message type: " + msg.getType());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // 모든 방에서 제거
        rooms.keySet().forEach(roomId -> leaveRoom(roomId, session));
        System.out.println("WebSocket closed: " + session.getId());
    }

    private void leaveRoom(Long roomId, WebSocketSession session) {
        Set<WebSocketSession> sessions = rooms.get(roomId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                rooms.remove(roomId);
            }
        }
    }
}