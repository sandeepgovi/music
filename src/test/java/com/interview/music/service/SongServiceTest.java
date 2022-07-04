package com.interview.music.service;

import com.interview.music.dto.request.SongRequest;
import com.interview.music.dto.request.Type;
import com.interview.music.entity.Album;
import com.interview.music.entity.Artist;
import com.interview.music.entity.Song;
import com.interview.music.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.*;

/**
 * @author Sandeep on 7/4/2022
 */
@ExtendWith(MockitoExtension.class)
public class SongServiceTest {

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private SongService songService;

    private Song song;
    private Album album;

    @BeforeEach
    void setUp() {
        album = new Album("Drones", 2015, LocalDate.now(), LocalDate.now(), new Artist());
        album.setId(1L);
        song = new Song("Tere Bin", 1, LocalDate.now(), LocalDate.now(), album);
    }

    @Test
    void deleteSongByAlbumId() {
        doNothing().when(songRepository).deleteByAlbumId(isA(Long.class));
        songService.deleteSongByAlbumId(album.getId());
        verify(songRepository, times(1)).deleteByAlbumId(album.getId());
    }

    @Test
    void getSongById() {
        given(songRepository.getById(1L)).willReturn(song);
        assertEquals("Tere Bin", songService.getSongById(1L).getName());
    }

    @Test
    void getSongsByName() {
        given(songRepository.getByNameContainingIgnoreCase(
                isA(String.class), isA(Pageable.class))).willReturn(Collections.singletonList(song));
        assertTrue(songService.getSongsByName("Tere", Type.SONG, 10, 10).contains(song));
    }

    @Test
    void updateSong() {
        doNothing().when(songRepository).updateSongBySongId(isA(Long.class), isA(String.class));
        songService.updateSongBySongId(1L, new SongRequest("Tera"));
        verify(songRepository, times(1)).updateSongBySongId(1L, "Tera");
    }

    @Test
    void saveSong() {
        SongRequest songRequest = new SongRequest("Tere Bin");
        given(songRepository.findCountOfSongsByAlbumId(album.getId())).willReturn(0);
        given(songRepository.save(isA(Song.class))).willReturn(song);
        assertEquals("Tere Bin", songService.saveSong(songRequest, album).getName());
    }
}