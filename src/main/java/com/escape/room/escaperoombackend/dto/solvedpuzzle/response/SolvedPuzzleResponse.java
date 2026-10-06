package com.escape.room.escaperoombackend.dto.solvedpuzzle.response;

import com.escape.room.escaperoombackend.domain.solvedpuzzle.SolvedPuzzle;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SolvedPuzzleResponse {

    private final Long id;
    private final Long puzzleId;
    private final String puzzleTitle;
    private final LocalDateTime solvedAt;
    private final Integer attemptCount;
    private final Integer hintUsedCount;

    public SolvedPuzzleResponse(SolvedPuzzle solvedPuzzle) {

        this.id = solvedPuzzle.getId();

        this.puzzleId =
                solvedPuzzle.getPuzzle().getId();

        this.puzzleTitle =
                solvedPuzzle.getPuzzle().getTitle();

        this.solvedAt =
                solvedPuzzle.getSolvedAt();

        this.attemptCount =
                solvedPuzzle.getAttemptCount();

        this.hintUsedCount =
                solvedPuzzle.getHintUsedCount();
    }
}