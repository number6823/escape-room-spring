package com.escape.room.escaperoombackend.domain.gameprogress;

import com.escape.room.escaperoombackend.domain.gamerecord.GameRecord;
import com.escape.room.escaperoombackend.domain.puzzle.Puzzle;
import com.escape.room.escaperoombackend.domain.room.Room;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "game_progress",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_game_progress_game_record_room",
                        columnNames = {"game_record_id", "room_id"}
                )
        }
)
public class GameProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_record_id", nullable = false)
    private GameRecord gameRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_puzzle_id")
    private Puzzle currentPuzzle;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "is_cleared", nullable = false)
    private boolean isCleared = false;


    protected GameProgress() {}

    public GameProgress(
            GameRecord gameRecord,
            Room room,
            Puzzle currentPuzzle
    ) {
        this.gameRecord = gameRecord;
        this.room = room;
        this.currentPuzzle = currentPuzzle;
    }

    public void updateCurrentPuzzle(Puzzle currentPuzzle) {
        this.currentPuzzle = currentPuzzle;
    }

    public void clear() {
        this.isCleared = true;
        this.completedAt = LocalDateTime.now();
        this.currentPuzzle = null;
    }
}
