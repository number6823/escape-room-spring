package com.escape.room.escaperoombackend.dto.hint.response;

import com.escape.room.escaperoombackend.domain.hint.Hint;
import lombok.Getter;

@Getter
public class HintResponse {
    private final Long id;
    private final Integer hintLevel;
    private final String content;
    private final Integer penaltySeconds;

    public HintResponse(Hint hint) {
        this.id = hint.getId();
        this.hintLevel = hint.getHintLevel();
        this.content = hint.getContent();
        this.penaltySeconds = hint.getPenaltySeconds();
    }
}
