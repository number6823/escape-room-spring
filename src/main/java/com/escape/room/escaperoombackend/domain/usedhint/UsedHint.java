package com.escape.room.escaperoombackend.domain.usedhint;

import com.escape.room.escaperoombackend.domain.game.GameRecord;
import com.escape.room.escaperoombackend.domain.hint.Hint;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "used_hints")
public class UsedHint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_record_id", nullable = false)
    private GameRecord gameRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hint_id", nullable = false)
    private Hint hint;

    @Column(name = "used_at", nullable = false)
    private LocalDateTime usedAt;

    protected UsedHint() {

    }

    public UsedHint(
            GameRecord gameRecord,
            Hint hint,
            LocalDateTime usedAt
    ) {
        this.gameRecord = gameRecord;
        this.hint = hint;
        this.usedAt = usedAt;
    }
}
