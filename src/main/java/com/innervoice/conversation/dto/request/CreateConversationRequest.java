package com.innervoice.conversation.dto.request;

public record CreateConversationRequest(
        Long userId,
        Long receiverId,
        Long characterImageId
) {
}
