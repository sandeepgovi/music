package com.interview.music.repository;

import com.interview.music.entity.Album;
import com.interview.music.entity.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sandeep on 7/4/2022
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class AlbumRepositoryTest {

    @Autowired
    private AlbumRepository albumRepository;

    private Album album;

    @BeforeEach
    void setUp() {
        Artist artist = new Artist();
        artist.setId(1L);
        artist.setName("AR Rehman");
        album = new Album("Roja", 1990, LocalDate.now(), LocalDate.now(), artist);
    }

    @Test
    @Transactional
    void findAndDeleteById() {
        Album saved = albumRepository.save(album);
        assertThat(albumRepository.findById(saved.getId()).get()).hasFieldOrPropertyWithValue("name", "Roja");
        albumRepository.deleteById(saved.getId());
        assertThat(albumRepository.findById(saved.getId()).isEmpty()).isTrue();
    }

    @Test
    void saveAndUpdateAlbum() {
        Album saved = albumRepository.save(album);
        assertThat(saved).hasFieldOrPropertyWithValue("name", "Roja");
        album.setName("Roja Roja");
        Album updated = albumRepository.save(album);
        assertThat(updated).hasFieldOrPropertyWithValue("name", "Roja Roja");
    }

    @Test
    void getOnlyAlbum() {
        assertThat(albumRepository.findById(1L).get().getSongSet().isEmpty()).isFalse();
        assertThat(albumRepository.getOnlyAlbum(1L).getSongSet()).isEmpty();
    }


}