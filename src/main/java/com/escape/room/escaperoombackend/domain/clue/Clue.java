package com.escape.room.escaperoombackend.domain.clue;

import com.escape.room.escaperoombackend.domain.puzzle.Puzzle;
import com.escape.room.escaperoombackend.domain.room.Room;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "clues")
public class Clue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puzzle_id")
    private Puzzle puzzle;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "clue_type", nullable = false, length = 30)
    private String clueType;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    protected Clue() {}

    public Clue(
            Room room,
            Puzzle puzzle,
            String name,
            String description,
            String clueType,
            String imageUrl
    ) {
        this.room = room;
        this.puzzle = puzzle;
        this.name = name;
        this.description = description;
        this.clueType = clueType;
        this.imageUrl = imageUrl;
    }
}
