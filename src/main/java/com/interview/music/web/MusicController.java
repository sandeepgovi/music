package com.interview.music.web;

import com.interview.music.dto.AlbumResponse;
import com.interview.music.dto.ArtistResponse;
import com.interview.music.dto.SongResponse;
import com.interview.music.dto.mapper.Mapper;
import com.interview.music.dto.request.AlbumRequest;
import com.interview.music.dto.request.ArtistRequest;
import com.interview.music.dto.request.SongRequest;
import com.interview.music.dto.request.Type;
import com.interview.music.exception.ApiException;
import com.interview.music.service.AlbumService;
import com.interview.music.service.ArtistService;
import com.interview.music.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sandeep on 7/4/2022
 */
@RestController
@Validated
@Slf4j
public class MusicController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private SongService songService;

    //Artists
    @Tag(name = "artists")
    @Operation(description = "Save Artist to Db")
    @PostMapping(value = "/artist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArtistResponse> saveArtist(final @RequestBody() ArtistRequest artist) {
        return ResponseEntity.ok(artistService.saveArtist(artist));
    }

    @Tag(name = "artists")
    @Operation(description = "Get all Artists with an option to omit children from response")
    @GetMapping(value = "/artists", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArtistResponse>> findArtists(final @RequestParam(value = "skipChildren", required = false, defaultValue = "true") boolean skipChildren) {
        return ResponseEntity.ok(artistService.getArtists(skipChildren));
    }

    @Tag(name = "artists")
    @Operation(description = "Get Artist by Id")
    @GetMapping(value = "/artists/{artistId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArtistResponse> findArtistById(final @PathVariable @Min(1) long artistId) {
        return ResponseEntity.ok(Mapper.mapArtistEntityToDTO(artistService.findById(artistId).orElse(null)));
    }

    @Tag(name = "artists")
    @Operation(description = "Update Artist by Id")
    @PutMapping(value = "/artists/{artistId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArtistResponse> updateArtist(final @PathVariable @Min(1) long artistId, @Valid @RequestBody ArtistRequest artistRequest) {
        return ResponseEntity.ok(artistService.updateArtist(artistRequest, artistId));
    }

    @Tag(name = "artists")
    @Operation(description = "Delete Artist by Id")
    @DeleteMapping(value = "/artists/{id}")
    public ResponseEntity deleteArtistById(final @PathVariable @Min(1) long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.ok().build();
    }

    //Albums
    @Tag(name = "albums")
    @Operation(description = "Get Album by Id")
    @GetMapping(value = "/albums/{albumId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlbumResponse> findArtists(final @PathVariable @Min(1) int albumId) {
        return albumService.findById(albumId).map((album) ->
        {
            return ResponseEntity.ok(Mapper.mapAlbumEntityToDTO(album));
        }).orElse(ResponseEntity.ok(null));
    }

    @Tag(name = "albums")
    @Operation(description = "Add album to an artist")
    @PostMapping(value = "/artists/{artistId}/albums", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlbumResponse> saveAlbum(final @PathVariable @Min(1) long artistId, @RequestBody AlbumRequest albumRequest) {
        return ResponseEntity.ok(albumService.saveAlbum(albumRequest, artistService.findById(artistId).orElse(null)));
    }

    @Tag(name = "albums")
    @Operation(description = "Update album using album Id")
    @PutMapping(value = "/artists/{artistId}/albums/{albumId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus updateAlbum(final @PathVariable @Min(1) long artistId, final @PathVariable @Min(1) long albumId, @Valid @RequestBody AlbumRequest albumRequest) {
        albumService.updateAlbum(albumRequest, artistService.findById(artistId).orElse(null), albumId);
        return HttpStatus.OK;
    }

    @Tag(name = "albums")
    @Operation(description = "Delete album using album Id")
    @DeleteMapping(value = "/albums/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus deleteAlbumById(final @PathVariable @Min(1) long id) {
        albumService.deleteAlbum(id);
        return HttpStatus.OK;
    }

    //Songs
    @Tag(name = "songs")
    @Operation(description = "Add song to an album")
    @PostMapping(value = "/albums/{albumId}/song", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SongResponse> saveSong(final @RequestBody() SongRequest songRequest, @PathVariable @Min(1) long albumId) {
        return albumService.findById(albumId).map(album -> {
            return ResponseEntity.ok(Mapper.mapSongDTOtoEntity(songService.saveSong(songRequest, album)));
        }).orElseThrow(() -> {
            throw new ApiException(null, "Album does not exist.", HttpStatus.BAD_REQUEST);
        });
    }

    @Tag(name = "songs")
    @Operation(description = "Get song by song id")
    @GetMapping(value = "/songs/{songId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SongResponse> getSongById(final @PathVariable @Min(1) int songId) {
        return ResponseEntity.ok(Mapper.mapSongDTOtoEntity(songService.getSongById(songId)));
    }

    @Tag(name = "songs")
    @Operation(description = "Get songs by name of song or album or artis")
    @GetMapping(value = "/songs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SongResponse>>
    getSongByName(final @RequestParam @NotBlank String name,
                  @RequestParam (required = false)Type type,
                  @RequestParam(defaultValue = "0") int page,
                  @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(songService.getSongsByName(name, type, page, size).stream().map(Mapper::mapSongDTOtoEntity).collect(Collectors.toList()));
    }

    @Tag(name = "songs")
    @Operation(description = "Update song")
    @PutMapping(value = "/songs/{songId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus updateSong(final @PathVariable @Min(1) long songId, @Valid @RequestBody SongRequest songRequest) {
        songService.updateSongBySongId(songId, songRequest);
        return HttpStatus.OK;
    }

    @Tag(name = "songs")
    @DeleteMapping(value = "/songs/{id}")
    @Operation(description = "Delete song")
    public HttpStatus deleteSongById(final @PathVariable @Min(1) long id) {
        songService.deleteSongByAlbumId(id);
        return HttpStatus.OK;
    }
}
