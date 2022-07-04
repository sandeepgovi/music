package com.interview.music.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Sandeep on 7/4/2022
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class AlbumResponse {
    private Long id;
    private String name;
    private Integer yearReleased;
    private LocalDate dateCreated;
    private LocalDate modified;
    private List<SongResponse> songs;
}
