package com.escape.room.escaperoombackend.dto.solvedpuzzle.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SolvePuzzleRequest {

    @NotBlank
    private String answer;
}
