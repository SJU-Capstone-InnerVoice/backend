package com.innervoice.character.domain;

import com.innervoice.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "character_images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CharacterImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CharacterImageType type;

    @Builder
    public CharacterImage(User user, String name, String imageUrl, CharacterImageType type) {
        this.user = user;
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = type;
    }
}