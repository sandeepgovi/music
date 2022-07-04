package com.interview.music.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumRequest {
    @NotNull
    @NotBlank
    @JsonProperty(required = true)
    private String name;
    @Min(1)
    @Max(9999)
    private Integer yearReleased;
}
