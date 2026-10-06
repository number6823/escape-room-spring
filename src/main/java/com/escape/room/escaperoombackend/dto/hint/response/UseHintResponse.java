package com.escape.room.escaperoombackend.dto.hint.response;

import lombok.Getter;

@Getter
public class UseHintResponse {

    private final Long hintId;
    private final Integer hintLevel;
    private final String content;
    private final Integer penaltySeconds;

    public UseHintResponse(
            Long hintId,
            Integer hintLevel,
            String content,
            Integer penaltySeconds
    ) {
        this.hintId = hintId;
        this.hintLevel = hintLevel;
        this.content = content;
        this.penaltySeconds = penaltySeconds;
    }
}