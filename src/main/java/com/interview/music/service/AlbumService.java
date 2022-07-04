package com.interview.music.service;

import com.interview.music.dto.AlbumResponse;
import com.interview.music.dto.mapper.Mapper;
import com.interview.music.dto.request.AlbumRequest;
import com.interview.music.entity.Album;
import com.interview.music.entity.Artist;
import com.interview.music.exception.ApiException;
import com.interview.music.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Sandeep on 7/4/2022
 */
@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Transactional
    public AlbumResponse saveAlbum(final AlbumRequest albumRequest, final Artist artist) {
        return Mapper.mapOnlyAlbumEntityToDTO(albumRepository.save(new Album
                (albumRequest.getName(), albumRequest.getYearReleased(), LocalDate.now(), LocalDate.now(), artist)));
    }

    @Transactional
    public void updateAlbum(final AlbumRequest albumRequest, final Artist artist, final long albumId) {
        if (artist == null) {
            throw new ApiException(null, "Artist does not exist.", HttpStatus.BAD_REQUEST);
        }
        Album album = albumRepository.findById(albumId).orElseThrow(() -> {
            throw new ApiException(null, "No Album Id with : " + albumId + " is available.", HttpStatus.BAD_REQUEST);
        });
        album.setName(albumRequest.getName());
        album.setYearReleased(albumRequest.getYearReleased());
        album.setLastModified(LocalDate.now());
        if (!album.getArtist().getId().equals(artist.getId())) album.setArtist(artist);
        albumRepository.save(album);
    }

    @Transactional
    public void deleteAlbum(final long albumId) {
        albumRepository.deleteById(albumId);
    }

    @Transactional
    public Optional<Album> findById(long albumId) {
        return albumRepository.findById(albumId);
    }
}
