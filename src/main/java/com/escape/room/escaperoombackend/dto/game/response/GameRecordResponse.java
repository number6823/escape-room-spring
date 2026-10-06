package com.escape.room.escaperoombackend.dto.game.response;

import com.escape.room.escaperoombackend.domain.game.GameRecord;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GameRecordResponse {

    private final Long id;
    private final LocalDateTime startedAt;
    private final LocalDateTime completedAt;
    private final Integer playTimeSeconds;
    private final Integer hintCount;
    private final String clearStatus;

    public GameRecordResponse(GameRecord gameRecord) {
        this.id = gameRecord.getId();
        this.startedAt = gameRecord.getStartedAt();
        this.completedAt = gameRecord.getCompletedAt();
        this.playTimeSeconds = gameRecord.getPlayTimeSeconds();
        this.hintCount = gameRecord.getHintCount();
        this.clearStatus = gameRecord.getClearStatus();
    }
}