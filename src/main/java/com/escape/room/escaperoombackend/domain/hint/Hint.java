package com.escape.room.escaperoombackend.domain.hint;

import com.escape.room.escaperoombackend.domain.puzzle.Puzzle;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "hints")
public class Hint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puzzle_id", nullable = false)
    private Puzzle puzzle;

    @Column(name = "hint_level", nullable = false)
    private Integer hintLevel;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "penalty_seconds", nullable = false)
    private Integer penaltySeconds = 0;

    protected Hint() {

    }
    public Hint(
            Puzzle puzzle,
            Integer hintLevel,
            String content,
            Integer penaltySeconds
    ) {
        this.puzzle = puzzle;
        this.hintLevel = hintLevel;
        this.content = content;
        this.penaltySeconds = penaltySeconds;
    }
}
