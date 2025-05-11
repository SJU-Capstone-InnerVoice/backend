package com.innervoice.talk.domain;

import com.innervoice.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "talks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Talk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private int duration;

    @Column(name = "start_at", nullable = false)
    private String startAt;

    @Builder
    public Talk(User user, String title, String content, int duration, String startAt) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.duration = duration;
        this.startAt = startAt;
    }
}