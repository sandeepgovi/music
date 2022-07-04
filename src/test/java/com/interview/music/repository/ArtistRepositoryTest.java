package com.interview.music.repository;

import com.interview.music.entity.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sandeep on 7/4/2022
 */
@DataJpaTest
class ArtistRepositoryTest {
    @Autowired
    private ArtistRepository artistRepository;

    private Artist artist;

    @BeforeEach
    void setUp() {
        artist = new Artist();
        artist.setName("AR Rehman");
    }

    @Test
    void findAndDeleteArtist() {
        Artist saved = artistRepository.save(artist);
        assertThat(artistRepository.findById(saved.getId()).get()).hasFieldOrPropertyWithValue("name", "AR Rehman");
        artistRepository.deleteById(saved.getId());
        assertThat(artistRepository.findById(saved.getId()).isEmpty()).isTrue();
    }

    @Test
    void saveAndupdateArtistName() {
        Artist saved = artistRepository.save(artist);
        assertThat(artistRepository.findById(saved.getId()).get()).hasFieldOrPropertyWithValue("name", "AR Rehman");
        saved.setName("Rehman");
        Artist updated = artistRepository.save(saved);
        assertThat(updated).hasFieldOrPropertyWithValue("name", "Rehman");
    }

}