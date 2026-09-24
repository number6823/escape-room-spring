package com.escape.room.escaperoombackend.domain.gamerecord;

import com.escape.room.escaperoombackend.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "game_records")
public class GameRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "play_time_seconds")
    private Integer playTimeSeconds;

    @Column(name = "hint_count", nullable = false)
    private Integer hintCount = 0;

    @Column(name = "clear_status", nullable = false, length = 20)
    private String clearStatus;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    protected GameRecord() {}

    public GameRecord(
            User user,
            LocalDateTime startedAt,
            String clearStatus
    ) {
        this.user = user;
        this.startedAt = startedAt;
        this.clearStatus = clearStatus;
    }

    public void complete() {

        LocalDateTime now = LocalDateTime.now();

        this.completedAt = now;

        this.playTimeSeconds =
                (int) Duration.between(
                        this.startedAt,
                        now
                ).getSeconds();

        this.clearStatus = "CLEARED";
    }
}
