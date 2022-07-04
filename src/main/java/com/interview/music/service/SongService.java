package com.interview.music.service;

import com.interview.music.dto.request.SongRequest;
import com.interview.music.dto.request.Type;
import com.interview.music.entity.Album;
import com.interview.music.entity.Song;
import com.interview.music.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Sandeep on 7/4/2022
 */
@Service
@Transactional
public class SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumService albumService;

    @Transactional
    public void deleteSongByAlbumId(long songId) {
        songRepository.deleteByAlbumId(songId);
    }

    public Song getSongById(long songId) {
        return songRepository.getById(songId);
    }

    public List<Song> getSongsByName(String name, Type type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        switch ((type != null) ? type : Type.SONG) {
            case ALBUM:
                return songRepository.getSongsByAlbumName(name, pageable);
            case ARTIST:
                return songRepository.getSongsByArtistName(name, pageable);
            default:
                return songRepository.getByNameContainingIgnoreCase(name, pageable);
        }
    }

    @Transactional
    public void updateSongBySongId(long songId, final SongRequest songRequest) {
        songRepository.updateSongBySongId(songId, songRequest.getName());
    }

    @Transactional
    public Song saveSong(SongRequest songRequest, Album album) {
        int lastTrackNumber = songRepository.findCountOfSongsByAlbumId(album.getId());
        return songRepository.save(new Song(songRequest.getName(), lastTrackNumber + 1, LocalDate.now(), LocalDate.now(), album));
    }
}
