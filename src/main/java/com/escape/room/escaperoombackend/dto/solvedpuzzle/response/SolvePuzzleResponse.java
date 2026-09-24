package com.escape.room.escaperoombackend.dto.solvedpuzzle.response;

import lombok.Getter;

@Getter
public class SolvePuzzleResponse {

    private final Long puzzleId;
    private final boolean solved;
    private final String message;

    public SolvePuzzleResponse(
            Long puzzleId,
            boolean solved,
            String message
    ) {
        this.puzzleId = puzzleId;
        this.solved = solved;
        this.message = message;
    }
}