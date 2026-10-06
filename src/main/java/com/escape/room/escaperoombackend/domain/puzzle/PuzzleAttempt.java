package com.escape.room.escaperoombackend.domain.puzzle;

import com.escape.room.escaperoombackend.domain.game.GameRecord;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "puzzle_attempts")
public class PuzzleAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_record_id", nullable = false)
    private GameRecord gameRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puzzle_id", nullable = false)
    private Puzzle puzzle;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

    @Column(name = "attempted_at", nullable = false)
    private LocalDateTime attemptedAt;

    protected PuzzleAttempt() {

    }

    public PuzzleAttempt(
            GameRecord gameRecord,
            Puzzle puzzle,
            boolean isCorrect,
            LocalDateTime attemptedAt
    ) {
        this.gameRecord = gameRecord;
        this.puzzle = puzzle;
        this.isCorrect = isCorrect;
        this.attemptedAt = attemptedAt;
    }
}
