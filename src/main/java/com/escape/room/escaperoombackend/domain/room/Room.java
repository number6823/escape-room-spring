package com.escape.room.escaperoombackend.domain.room;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "room_order", nullable = false)
    private Integer roomOrder;

    @Column(name = "background_image", length = 500)
    private String backgroundImage;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked = true;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    protected Room() {}

    public Room(
            String name,
            String description,
            Integer roomOrder,
            String backgroundImage,
            boolean isLocked
    ) {
        this.name = name;
        this.description = description;
        this.roomOrder = roomOrder;
        this.backgroundImage = backgroundImage;
        this.isLocked = isLocked;
    }

}