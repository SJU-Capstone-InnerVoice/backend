package com.innervoice.friend.domain;

import com.innervoice.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "friend_requests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "friend_id", nullable = false)
    private User friend;

    @Builder
    public FriendRequest(User user, User friend) {
        this.user = user;
        this.friend = friend;
    }
}