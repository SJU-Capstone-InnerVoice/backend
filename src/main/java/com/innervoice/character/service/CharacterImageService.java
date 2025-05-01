package com.innervoice.character.service;


import com.innervoice.character.domain.CharacterImage;
import com.innervoice.character.domain.CharacterImageType;
import com.innervoice.character.dto.request.CharacterImageUploadRequest;
import com.innervoice.character.dto.response.CharacterImageResponse;
import com.innervoice.character.repository.CharacterImageRepository;
import com.innervoice.common.domain.S3DirectoryName;
import com.innervoice.common.service.S3UploadService;
import com.innervoice.user.domain.User;
import com.innervoice.user.service.FindUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterImageService {

    private final S3UploadService s3UploadService;
    private final CharacterImageRepository characterImageRepository;
    private final FindUserService findUserService;

    public CharacterImageResponse uploadCharacterImage(CharacterImageUploadRequest request) {
        User user = null;
        if (request.type() == CharacterImageType.USER) {
            user = findUserService.findUserById(request.userId());
        }
        String url = s3UploadService.uploadFile(
                S3DirectoryName.CHARACTER_IMAGE.getDirectoryName(),
                request.file()
        );
        CharacterImage image = CharacterImage.builder()
                .user(user)
                .name(request.name())
                .imageUrl(url)
                .type(request.type())
                .build();
        CharacterImage characterImage = characterImageRepository.save(image);
        return CharacterImageResponse.from(characterImage);
    }

}
