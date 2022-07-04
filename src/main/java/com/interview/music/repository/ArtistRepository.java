package com.interview.music.repository;

import com.interview.music.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Sandeep on 7/4/2022
 */
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    @Query(value = "select new Artist(a.id, a.name, a.created, a.lastModified) from Artist a")
    List<Artist> findOnlyArtists();

    @Modifying
    @Query(value = "update artist set name=:name where id=:id", nativeQuery = true)
    void updateArtistName(@Param("name") final String name, @Param("id") final long id);
}
