package com.interview.music.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.music.dto.AlbumResponse;
import com.interview.music.dto.ArtistResponse;
import com.interview.music.dto.request.ArtistRequest;
import com.interview.music.entity.Artist;
import com.interview.music.service.AlbumService;
import com.interview.music.service.ArtistService;
import com.interview.music.service.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sandeep on 7/4/2022
 */
@WebMvcTest
class MusicControllerTest {

    @MockBean
    private ArtistService artistService;
    @MockBean
    private AlbumService albumService;
    @MockBean
    private SongService songService;
    @Autowired
    private MockMvc mockMvc;
    private ArtistResponse artistResponse;
    private Artist artist;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        artistResponse = new ArtistResponse(1L, "Test", LocalDate.now(), LocalDate.now(), Collections.singletonList(new AlbumResponse()));
        artist = new Artist(1L, "Test", LocalDate.now(), LocalDate.now());
    }

    @Test
    void saveArtist() throws Exception {
        ArtistRequest artistRequest = new ArtistRequest("Test");
        given(artistService.saveArtist(artistRequest)).willReturn(artistResponse);

        mockMvc.perform(post("/artist")
                        .content(asJsonString(artistRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("name").value("Test"));
    }

    @Test
    void findArtists() throws Exception {
        given(artistService.getArtists(isA(Boolean.class))).willReturn(Collections.singletonList(artistResponse));
        mockMvc.perform(get("/artists")
                        .param("skipChildren", "false"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("[0].name").value("Test"));
    }

    @Test
    void findArtistById() throws Exception {
        given(artistService.findById(isA(Long.class))).willReturn(Optional.of(artist));
        mockMvc.perform(get("/artists/{artistId}", 1L))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("name").value("Test"));
    }

    @Test
    void deleteArtistById() throws Exception {
        doNothing().when(artistService).deleteArtist(isA(Long.class));
        mockMvc.perform(delete("/artists/{artistId}", 1L))
                .andExpect(status().isOk());
    }
}