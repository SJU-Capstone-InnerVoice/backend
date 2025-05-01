package com.innervoice.conversation.service;

import com.innervoice.conversation.domain.ConversationRequest;
import com.innervoice.conversation.dto.request.CreateConversationRequest;
import com.innervoice.conversation.dto.response.ConversationRequestResponse;
import com.innervoice.conversation.exception.ConversationException;
import com.innervoice.conversation.exception.ConversationExceptionType;
import com.innervoice.conversation.repository.ConversationRequestRepository;
import com.innervoice.user.domain.User;
import com.innervoice.user.service.FindUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ConversationRequestService {

    private final ConversationRequestRepository repository;
    private final FindUserService findUserService;

    public ConversationRequestResponse create(CreateConversationRequest request) {
        User user = findUserService.findUserById(request.userId());
        User receiver = findUserService.findUserById(request.receiverId());
        ConversationRequest conversationRequest = ConversationRequest.builder()
                .sender(user)
                .receiver(receiver)
                .roomId(request.roomId())
                .characterImageId(request.characterImageId())
                .build();
        ConversationRequest savedRequest = repository.save(conversationRequest);
        return ConversationRequestResponse.from(savedRequest);
    }

    public List<ConversationRequestResponse> getReceivedRequests(Long userId) {
        User receiver = findUserService.findUserById(userId);
        return repository.findAllByReceiverOrSender(userId)
                .stream()
                .map(ConversationRequestResponse::from)
                .toList();
    }

    public void accept(Long requestId) {
        ConversationRequest conversationRequest = repository.findById(requestId)
                .orElseThrow(() -> new ConversationException(ConversationExceptionType.CONVERSATION_REQUEST_NOT_FOUND));
        conversationRequest.accept();
    }

    public void delete(Long requestId) {
        ConversationRequest conversationRequest = repository.findById(requestId)
                .orElseThrow(() -> new ConversationException(ConversationExceptionType.CONVERSATION_REQUEST_NOT_FOUND));
        repository.delete(conversationRequest);
    }
}
