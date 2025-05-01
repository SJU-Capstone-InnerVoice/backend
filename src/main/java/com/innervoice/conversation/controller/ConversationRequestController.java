package com.innervoice.conversation.controller;

import com.innervoice.conversation.dto.request.CreateConversationRequest;
import com.innervoice.conversation.dto.response.ConversationRequestResponse;
import com.innervoice.conversation.service.ConversationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversations")
@RequiredArgsConstructor
public class ConversationRequestController {

    private final ConversationRequestService conversationRequestService;

    @PostMapping
    public ResponseEntity<ConversationRequestResponse> create(@RequestBody CreateConversationRequest request) {
        ConversationRequestResponse response = conversationRequestService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ConversationRequestResponse>> getReceived(@RequestParam("userId") Long userId) {
        List<ConversationRequestResponse> response = conversationRequestService.getReceivedRequests(userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/accept")
    public ResponseEntity<Void> accept(@PathVariable("id") Long id) {
        conversationRequestService.accept(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        conversationRequestService.delete(id);
        return ResponseEntity.ok().build();
    }
}
