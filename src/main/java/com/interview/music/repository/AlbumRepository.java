package com.interview.music.repository;

import com.interview.music.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Sandeep on 7/4/2022
 */
public interface AlbumRepository extends JpaRepository<Album, Long> {
    @Query(value = "select new Album(a.id, a.name, a.yearReleased, a.created, a.lastModified) from Album a where a.id =:id")
    Album getOnlyAlbum(@Param("id") long id);
}
