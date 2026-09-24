package com.escape.room.escaperoombackend.dto.clue.response;

import com.escape.room.escaperoombackend.domain.clue.Clue;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ClueResponse {

    private final Long id;
    private final Long roomId;
    private final Long puzzleId;
    private final String name;
    private final String description;
    private final String clueType;
    private final String imageUrl;
    private final LocalDateTime createdAt;

    public ClueResponse(Clue clue) {
        this.id = clue.getId();
        this.roomId = clue.getRoom().getId();

        this.puzzleId =
                clue.getPuzzle() != null
                ? clue.getPuzzle().getId()
                : null;
        this.name = clue.getName();
        this.description = clue.getDescription();
        this.clueType = clue.getClueType();
        this.imageUrl = clue.getImageUrl();
        this.createdAt = clue.getCreatedAt();
    }
}
