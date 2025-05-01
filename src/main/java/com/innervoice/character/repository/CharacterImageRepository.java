package com.innervoice.character.repository;

import com.innervoice.character.domain.CharacterImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterImageRepository extends JpaRepository<CharacterImage, Long> {
}
