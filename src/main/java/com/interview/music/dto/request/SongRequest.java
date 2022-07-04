package com.interview.music.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Sandeep on 7/4/2022
 */
@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongRequest {
    @NotBlank
    @NotNull
    private String name;
}
