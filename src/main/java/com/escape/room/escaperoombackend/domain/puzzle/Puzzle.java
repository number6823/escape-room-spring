package com.escape.room.escaperoombackend.domain.puzzle;

import com.escape.room.escaperoombackend.domain.room.Room;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "puzzles")
public class Puzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "puzzle_type", nullable = false, length = 30)
    private String puzzleType;

    @Column(nullable = false, length = 255)
    private String answer;

    @Column(name = "puzzle_order", nullable = false)
    private Integer puzzleOrder;

    @Column(name = "is_required", nullable = false)
    private boolean isRequired = true;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    protected Puzzle() {}

    public Puzzle(
            Room room,
            String title,
            String description,
            String puzzleType,
            String answer,
            Integer puzzleOrder,
            boolean isRequired
    )
    {
        this.room = room;
        this.title = title;
        this.description = description;
        this.puzzleType = puzzleType;
        this.answer = answer;
        this.puzzleOrder = puzzleOrder;
        this.isRequired = isRequired;
    }
}
