package com.escape.room.escaperoombackend.dto.hint.response;

import com.escape.room.escaperoombackend.domain.usedhint.UsedHint;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UsedHintResponse {

    private final Long id;
    private final Long hintId;
    private final Integer hintLevel;
    private final String content;
    private final Integer penaltySeconds;
    private final LocalDateTime usedAt;

    public UsedHintResponse(UsedHint usedHint) {

        this.id = usedHint.getId();

        this.hintId = usedHint.getHint().getId();

        this.hintLevel =
                usedHint.getHint().getHintLevel();

        this.content =
                usedHint.getHint().getContent();

        this.penaltySeconds =
                usedHint.getHint().getPenaltySeconds();

        this.usedAt =
                usedHint.getUsedAt();
    }
}