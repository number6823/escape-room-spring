package com.escape.room.escaperoombackend.dto.puzzle.response;


import com.escape.room.escaperoombackend.domain.puzzle.Puzzle;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PuzzleResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final String puzzleType;
    private final Integer puzzleOrder;
    private final boolean isRequired;
    private final LocalDateTime createdAt;

    public PuzzleResponse(Puzzle puzzle) {
        this.id = puzzle.getId();
        this.title = puzzle.getTitle();
        this.description = puzzle.getDescription();
        this.puzzleType = puzzle.getPuzzleType();
        this.puzzleOrder = puzzle.getPuzzleOrder();
        this.isRequired = puzzle.isRequired();
        this.createdAt = puzzle.getCreatedAt();
    }
}
