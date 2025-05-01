package com.innervoice.character.controller;

import com.innervoice.character.dto.request.CharacterImageUploadRequest;
import com.innervoice.character.dto.response.CharacterImageResponse;
import com.innervoice.character.service.CharacterImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/character-images")
@RequiredArgsConstructor
public class CharacterImageController {

    private final CharacterImageService characterImageService;

    @PostMapping("/upload")
    public ResponseEntity<CharacterImageResponse> uploadCharacterImage(@ModelAttribute CharacterImageUploadRequest request) {
        CharacterImageResponse response = characterImageService.uploadCharacterImage(request);
        return ResponseEntity.ok(response);
    }
}