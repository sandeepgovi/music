package com.interview.music.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Sandeep on 7/4/2022
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@SequenceGenerator(name = "SEQ_ID", sequenceName = "SEQ_ALBUM", initialValue = 5, allocationSize = 1)
@ToString(exclude = {"artist"})
@Builder
public class Album extends BaseEntitty {
    @Column
    @EqualsAndHashCode.Include
    private String name;
    @Column
    private Integer yearReleased;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "artist_albums",
            joinColumns = {@JoinColumn(name = "albums_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "artist_id", referencedColumnName = "id")})
    private Artist artist;

    @OneToMany(targetEntity = Song.class,
            mappedBy = "album",
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<Song> songSet = new HashSet<>();

    public Album(final String name, final Integer yearReleased, final LocalDate created, final LocalDate lastModified, final Artist artist) {
        super(created, lastModified);
        this.name = name;
        this.yearReleased = yearReleased;
        this.artist = artist;
    }

    public Album(final Long id, final String name, final Integer yearReleased, final LocalDate created, final LocalDate lastModified) {
        super(id, created, lastModified);
        this.name = name;
        this.yearReleased = yearReleased;
    }

    public void addSong(final Song song) {
        songSet.add(song);
        song.setAlbum(this);
    }

    public void removeSong(final Song song) {
        songSet.remove(song);
        song.setAlbum(null);
    }


}
