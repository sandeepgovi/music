package com.interview.music.repository;

import com.interview.music.entity.Song;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Sandeep on 7/4/2022
 */
public interface SongRepository extends JpaRepository<Song, Long> {
    @Modifying
    @Query("DELETE Song s WHERE s.album.id = ?1")
    void deleteByAlbumId(final long id);

    @Query(value = "select count(*) from song where album_id=:albumId", nativeQuery = true)
    int findCountOfSongsByAlbumId(final @Param("albumId") long albumId);

    List<Song> getByNameContainingIgnoreCase(final String nameLike, Pageable pageable);

    @Modifying
    @Query("update Song s set s.name=:name where s.id=:id")
    void updateSongBySongId(@Param("id") final long id, @Param("name") final String name);

    @Query("select s from Song s where lower(s.album.name) like lower(concat('%', :albumName,'%'))")
    List<Song> getSongsByAlbumName(@Param("albumName") final String albumName, Pageable pageable);

    @Query("select s from Song s where s.album.artist.name like %:artistName%")
    List<Song> getSongsByArtistName(@Param("artistName") final String artistName, Pageable pageable);
}
