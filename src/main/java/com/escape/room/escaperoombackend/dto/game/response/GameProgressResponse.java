package com.escape.room.escaperoombackend.dto.game.response;

import com.escape.room.escaperoombackend.domain.game.GameProgress;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GameProgressResponse {

    private final Long id;
    private final Long roomId;
    private final Long currentPuzzleId;
    private final LocalDateTime completedAt;
    private final boolean isCleared;

    public GameProgressResponse(GameProgress gameProgress) {
        this.id = gameProgress.getId();
        this.roomId = gameProgress.getRoom().getId();

        this.currentPuzzleId =
                gameProgress.getCurrentPuzzle() != null
                        ? gameProgress.getCurrentPuzzle().getId()
                        : null;

        this.completedAt = gameProgress.getCompletedAt();
        this.isCleared = gameProgress.isCleared();
    }
}