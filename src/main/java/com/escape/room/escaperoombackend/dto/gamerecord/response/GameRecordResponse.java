package com.escape.room.escaperoombackend.dto.gamerecord.response;

import com.escape.room.escaperoombackend.domain.gamerecord.GameRecord;

import java.time.LocalDateTime;

public class GameRecordResponse {

    private final Long id;
    private final LocalDateTime startedAt;
    private final String clearStatus;
    private final Integer hintCount;

    public GameRecordResponse(GameRecord gameRecord) {
        this.id = gameRecord.getId();
        this.startedAt = gameRecord.getStartedAt();
        this.clearStatus = gameRecord.getClearStatus();
        this.hintCount = gameRecord.getHintCount();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public String getClearStatus() {
        return clearStatus;
    }

    public Integer getHintCount() {
        return hintCount;
    }
}