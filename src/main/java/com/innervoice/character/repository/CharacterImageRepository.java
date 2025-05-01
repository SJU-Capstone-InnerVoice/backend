package com.innervoice.character.repository;

import com.innervoice.character.domain.CharacterImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CharacterImageRepository extends JpaRepository<CharacterImage, Long> {

    @Query("""
        SELECT ci FROM CharacterImage ci
        WHERE ci.type = 'DEFAULT'
           OR (ci.type = 'USER' AND ci.user.id = :userId)
    """)
    List<CharacterImage> findAllAvailableForUser(@Param("userId") Long userId);
}
