package com.innervoice.character.controller;

import com.innervoice.character.dto.request.CharacterImageUploadRequest;
import com.innervoice.character.dto.response.CharacterImageResponse;
import com.innervoice.character.service.CharacterImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterImageController {

    private final CharacterImageService characterImageService;

    @PostMapping
    public ResponseEntity<CharacterImageResponse> uploadCharacterImage(@ModelAttribute CharacterImageUploadRequest request) {
        CharacterImageResponse response = characterImageService.uploadCharacterImage(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CharacterImageResponse>> getAllAvailableCharacterImages(@RequestParam("userId") Long userId) {
        List<CharacterImageResponse> responseList = characterImageService.listAllCharacterImagesForUser(userId);
        return ResponseEntity.ok(responseList);
    }
}