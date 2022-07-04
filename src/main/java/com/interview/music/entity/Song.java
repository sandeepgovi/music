package com.interview.music.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;

/**
 * @author Sandeep on 7/4/2022
 */
@Entity
@Data
@ToString(exclude = {"album"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@SequenceGenerator(name = "SEQ_ID", sequenceName = "SEQ_SONG", initialValue = 46, allocationSize = 1)
public class Song extends BaseEntitty {
    @Column
    private int track;
    @Column
    @EqualsAndHashCode.Include
    private String name;
    @ManyToOne(targetEntity = Album.class, fetch = FetchType.LAZY)
    private Album album;

    public Song(String name, int track, LocalDate created, LocalDate updated, Album album) {
        super(created, updated);
        this.name = name;
        this.track = track;
        this.album = album;
    }
}
