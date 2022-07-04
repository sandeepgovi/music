package com.interview.music.dto.mapper;

import com.interview.music.dto.AlbumResponse;
import com.interview.music.dto.ArtistResponse;
import com.interview.music.dto.SongResponse;
import com.interview.music.entity.Album;
import com.interview.music.entity.Artist;
import com.interview.music.entity.Song;
import com.interview.music.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

/**
 * @author Sandeep on 7/4/2022
 */
public class Mapper {
    public static ArtistResponse mapArtistEntityToDTO(final Artist artist) {
        if (artist == null) {
            throw new ApiException(null, "No Artist Found", HttpStatus.NO_CONTENT);
        }
        var albums = new ArrayList<AlbumResponse>();
        artist.getAlbums().forEach(album -> albums.add(mapAlbumEntityToDTO(album)));
        return new ArtistResponse(artist.getId(), artist.getName(),
                artist.getCreated(), artist.getLastModified(),
                albums);
    }

    public static ArtistResponse mapOnlyArtistEntityToDTO(final Artist artist) {
        if (artist == null) {
            throw new ApiException(null, "No Artist Found", HttpStatus.NO_CONTENT);
        }
        return ArtistResponse.builder().id(artist.getId())
                .name(artist.getName())
                .dateCreated(artist.getCreated())
                .lastModified(artist.getLastModified())
                .build();
    }

    public static AlbumResponse mapAlbumEntityToDTO(final Album album) {
        if (album == null) {
            throw new ApiException(null, "No Album Found", HttpStatus.NO_CONTENT);
        }
        var songs = new ArrayList<SongResponse>();
        album.getSongSet().forEach(song -> songs.add(mapSongDTOtoEntity(song)));
        return new AlbumResponse(album.getId(), album.getName(), album.getYearReleased(), album.getCreated(), album.getLastModified(), songs);
    }

    public static AlbumResponse mapOnlyAlbumEntityToDTO(final Album album) {
        if (album == null) {
            throw new ApiException(null, "No Album Found", HttpStatus.NO_CONTENT);
        }
        return AlbumResponse.builder().id(album.getId())
                .name(album.getName())
                .yearReleased(album.getYearReleased())
                .dateCreated(album.getCreated())
                .modified(album.getLastModified()).build();
    }

    public static SongResponse mapSongDTOtoEntity(final Song song) {
        if (song == null) {
            throw new ApiException(null, "No Song Found", HttpStatus.NO_CONTENT);
        }
        return new SongResponse(song.getId(), song.getTrack(), song.getName());
    }
}
