package com.innervoice.character.dto.request;

import com.innervoice.character.domain.CharacterImageType;
import org.springframework.web.multipart.MultipartFile;

public record CharacterImageUploadRequest(
        Long userId,
        String name,
        CharacterImageType type,
        MultipartFile file
) {
}