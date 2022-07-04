package com.interview.music.service;

import com.interview.music.dto.request.ArtistRequest;
import com.interview.music.entity.Album;
import com.interview.music.entity.Artist;
import com.interview.music.repository.ArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author Sandeep on 7/4/2022
 */
@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {
    @Mock
    private ArtistRepository artistRepository;
    @InjectMocks
    private ArtistService artistService;

    private Artist artist;
    private Artist artistIncludingAlbums;

    @BeforeEach
    void setUp() {
        artist = new Artist("AR Rehman", LocalDate.now(), LocalDate.now());
        artist.setId(1L);
        artistIncludingAlbums = new Artist("AR Rehman", LocalDate.now(), LocalDate.now());
        artistIncludingAlbums.setAlbums(Collections.singletonList(new Album()));
    }

    @Test
    void saveArtist() {
        ArtistRequest artistRequest = new ArtistRequest("AR Rehman");
        given(artistRepository.save(isA(Artist.class))).willReturn(artist);
        assertEquals(1L, artistService.saveArtist(artistRequest).getId());
    }

    @Test
    void updateArtist() {
        ArtistRequest artistRequest = new ArtistRequest("AR Rehman");
        given(artistRepository.findById(1L)).willReturn(Optional.of(artist));
        given(artistRepository.save(isA(Artist.class))).willReturn(artist);
        artistService.updateArtist(artistRequest, 1L);
        verify(artistRepository, times(1)).save(artist);
    }

    @Test
    void deleteArtist() {
        doNothing().when(artistRepository).deleteById(isA(Long.class));
        artistService.deleteArtist(1L);
        verify(artistRepository, times(1)).deleteById(1L);
    }

    @Test
    void getArtists() {
        given(artistRepository.findAll()).willReturn(Collections.singletonList(artist));
        given(artistRepository.findOnlyArtists()).willReturn(Collections.singletonList(artistIncludingAlbums));
        assertEquals("AR Rehman", artistService.getArtists(false).get(0).getName());
        assertEquals("AR Rehman", artistService.getArtists(true).get(0).getName());
    }
}