package com.interview.music.repository;

import com.interview.music.entity.Album;
import com.interview.music.entity.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sandeep on 7/4/2022
 */
@DataJpaTest
class SongRepositoryTest {

    @Autowired
    private SongRepository songRepository;

    private Song song;

    @BeforeEach
    void setUp() {
        Album album = new Album();
        album.setId(1L);
        album.setName("Roja");
        song = new Song("Tera Bin", 1, LocalDate.now(), LocalDate.now(), album);
    }

    @Test
    void deleteByAlbumId() {
        Song saved = songRepository.save(song);
        assertThat(songRepository.findById(saved.getId()).get()).hasFieldOrPropertyWithValue("name", "Tera Bin");
        songRepository.deleteById(saved.getId());
        assertThat(songRepository.findById(saved.getId()).isEmpty()).isTrue();
    }

    @Test
    void findCountOfSongsByAlbumId() {
        int count = songRepository.findCountOfSongsByAlbumId(song.getAlbum().getId());
        Song saved = songRepository.save(song);
        assertThat(songRepository.findCountOfSongsByAlbumId(saved.getAlbum().getId())).isEqualTo(count + 1);
    }

    @Test
    void getByNameContaining() {
        songRepository.save(song);
        assertThat(songRepository.getByNameContainingIgnoreCase("tera Bin", Pageable.ofSize(10)).size()).isEqualTo(1);
    }

    @Test
    void updateSongBySongId() {
        Song saved = songRepository.save(song);
        saved.setName("Dil se re");
        assertThat(songRepository.findById(saved.getId()).get().getName()).isEqualTo("Dil se re");

    }

    @Test
    void getSongsByAlbumName() {
        assertThat(songRepository.getSongsByAlbumName("Drones", Pageable.ofSize(10)).size()).isEqualTo(10);
    }

    @Test
    void getSongsByArtistName() {
        assertThat(songRepository.getSongsByArtistName("Duran Duran", Pageable.ofSize(9)).size()).isEqualTo(9);
    }
}