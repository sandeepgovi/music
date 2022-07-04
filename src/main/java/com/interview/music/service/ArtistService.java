package com.interview.music.service;

import com.interview.music.dto.ArtistResponse;
import com.interview.music.dto.mapper.Mapper;
import com.interview.music.dto.request.ArtistRequest;
import com.interview.music.entity.Artist;
import com.interview.music.exception.ApiException;
import com.interview.music.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Sandeep on 7/4/2022
 */
@Service
@Transactional
public class ArtistService {
    @Autowired
    private ArtistRepository artistRepository;

    public ArtistResponse saveArtist(ArtistRequest artistRequest) {
        return Mapper.mapOnlyArtistEntityToDTO(artistRepository.save(new Artist(artistRequest.getName(), LocalDate.now(), LocalDate.now())));
    }

    public ArtistResponse updateArtist(final ArtistRequest artistRequest, final long artistId) {
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> {
            throw new ApiException(null, "Artist with Id: " + artistId + "not found", HttpStatus.BAD_REQUEST);
        });
        artist.setName(artistRequest.getName());
        artist.setLastModified(LocalDate.now());
        return Mapper.mapOnlyArtistEntityToDTO(artistRepository.save(artist));
    }

    public void deleteArtist(final long artistId) {
        artistRepository.deleteById(artistId);
    }

    public List<ArtistResponse> getArtists(final Boolean skipChildren) {
        if (skipChildren) {
            return artistRepository.findOnlyArtists()
                    .stream()
                    .map(Mapper::mapOnlyArtistEntityToDTO)
                    .collect(Collectors.toList());
        }
        return artistRepository.findAll()
                .stream()
                .map(Mapper::mapArtistEntityToDTO)
                .collect(Collectors.toList());
    }

    public Optional<Artist> findById(final long id) {
        return artistRepository.findById(id);
    }

}
