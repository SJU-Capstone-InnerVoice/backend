package com.innervoice.character.dto.response;

import com.innervoice.character.domain.CharacterImage;
import com.innervoice.character.domain.CharacterImageType;

public record CharacterImageResponse(
        Long id,
        String name,
        String imageUrl,
        CharacterImageType type
) {
    public static CharacterImageResponse from(CharacterImage entity) {
        return new CharacterImageResponse(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                entity.getType()
        );
    }
}