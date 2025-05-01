package com.innervoice.conversation.domain;

import com.innervoice.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "conversation_requests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConversationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "character_image_id", nullable = false)
    private Long characterImageId;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "is_accepted", nullable = false)
    private boolean isAccepted;

    @Builder
    public ConversationRequest(User sender, User receiver, Long characterImageId, Long roomId) {
        this.sender = sender;
        this.receiver = receiver;
        this.characterImageId = characterImageId;
        this.roomId = roomId;
        this.isAccepted = false;
    }

    public void accept() {
        this.isAccepted = true;
    }
}