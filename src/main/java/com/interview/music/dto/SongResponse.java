package com.interview.music.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Sandeep on 7/4/2022
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class SongResponse {
    private long id;
    private int track;
    private String name;
}
