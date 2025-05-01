package com.innervoice.conversation.repository;


import com.innervoice.conversation.domain.ConversationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationRequestRepository extends JpaRepository<ConversationRequest, Long> {

    @Query("""
                SELECT cr FROM ConversationRequest cr
                WHERE cr.receiver.id = :userId OR cr.sender.id = :userId
            """)
    List<ConversationRequest> findAllByReceiverOrSender(@Param("userId") Long userId);
}