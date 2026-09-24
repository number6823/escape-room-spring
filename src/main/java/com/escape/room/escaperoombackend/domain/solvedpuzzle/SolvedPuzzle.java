package com.escape.room.escaperoombackend.domain.solvedpuzzle;

import com.escape.room.escaperoombackend.domain.gamerecord.GameRecord;
import com.escape.room.escaperoombackend.domain.puzzle.Puzzle;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "solved_puzzles",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_solved_puzzle_game_record_puzzle",
                        columnNames = {"game_record_id", "puzzle_id"}
                )
        }
)
public class SolvedPuzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_record_id", nullable = false)
    private GameRecord gameRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puzzle_id", nullable = false)
    private Puzzle puzzle;

    @Column(name = "solved_at", nullable = false)
    private LocalDateTime solvedAt;

    @Column(name = "attempt_count",nullable = false)
    private Integer attemptCount;

    @Column(name = "hint_used_count", nullable = false)
    private Integer hintUsedCount;

    protected SolvedPuzzle() {}

    public SolvedPuzzle(
            GameRecord gameRecord,
            Puzzle puzzle,
            LocalDateTime solvedAt,
            Integer attemptCount,
            Integer hintUsedCount
    ) {
        this.gameRecord = gameRecord;
        this.puzzle = puzzle;
        this.solvedAt = solvedAt;
        this.attemptCount = attemptCount;
        this.hintUsedCount = hintUsedCount;
    }
}
