package com.interview.music.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sandeep on 7/4/2022
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@SequenceGenerator(name = "SEQ_ID", sequenceName = "SEQ_ARTIST", initialValue = 4, allocationSize = 1)
public class Artist extends BaseEntitty {
    @Column
    @EqualsAndHashCode.Include
    private String name;

    @OneToMany(targetEntity = Album.class,
            fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "artist")
    private List<Album> albums = new ArrayList<>();

    public Artist(final String name, final LocalDate created, final LocalDate lastModified) {
        super(created, lastModified);
        this.name = name;
    }

    public Artist(final long id, final String name, final LocalDate created, final LocalDate lastModified) {
        super(id, created, lastModified);
        this.name = name;
    }

    public void addAlbum(final Album album) {
        albums.add(album);
        album.setArtist(this);
    }

    public void removeAlbum(final Album album) {
        albums.remove(album);
        album.setArtist(null);
    }
}
