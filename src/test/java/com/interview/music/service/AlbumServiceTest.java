package com.interview.music.service;

import com.interview.music.dto.request.AlbumRequest;
import com.interview.music.entity.Album;
import com.interview.music.entity.Artist;
import com.interview.music.repository.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author Sandeep on 7/4/2022
 */
@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumService albumService;

    private Album album;
    private Artist artist;

    @BeforeEach
    void setUp() {
        artist = new Artist();
        artist.setId(1L);
        album = new Album(1L, "Drones", 2015, LocalDate.now(), LocalDate.now());
        album.setArtist(artist);
    }

    @Test
    void saveAlbum() {
        AlbumRequest albumRequest = new AlbumRequest("Roja", 1990);
        given(albumRepository.save(isA(Album.class))).willReturn(album);
        assertEquals(1L, albumService.saveAlbum(albumRequest, artist).getId());
    }

    @Test
    void updateAlbum() {
        given(albumRepository.save(isA(Album.class))).willReturn(album);
        given(albumRepository.findById(1L)).willReturn(Optional.of(album));
        albumService.updateAlbum(new AlbumRequest("Roja", 1990), new Artist(), 1L);
        verify(albumRepository, times(1)).save(album);
    }

    @Test
    void deleteAlbum() {
        doNothing().when(albumRepository).deleteById(isA(Long.class));
        albumService.deleteAlbum(album.getId());
        verify(albumRepository, times(1)).deleteById(album.getId());
    }

    @Test
    void findById() {
        given(albumRepository.findById(1L)).willReturn(Optional.of(album));
        assertEquals("Drones", albumService.findById(1L).get().getName());
    }
}