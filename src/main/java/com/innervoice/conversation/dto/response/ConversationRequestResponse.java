package com.innervoice.conversation.dto.response;

import com.innervoice.conversation.domain.ConversationRequest;

public record ConversationRequestResponse(
        Long id,
        Long senderId,
        Long receiverId,
        Long roomId,
        Long characterImageId,
        boolean isAccepted
) {
    public static ConversationRequestResponse from(ConversationRequest entity) {
        return new ConversationRequestResponse(
                entity.getId(),
                entity.getSender().getId(),
                entity.getReceiver().getId(),
                entity.getRoomId(),
                entity.getCharacterImageId(),
                entity.isAccepted()
        );
    }
}