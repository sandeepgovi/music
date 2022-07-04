package com.interview.music.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Sandeep on 7/4/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistRequest {
    @NotNull
    @NotBlank
    private String name;
}