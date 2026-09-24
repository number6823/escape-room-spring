package com.escape.room.escaperoombackend.domain.item;

import com.escape.room.escaperoombackend.domain.room.Room;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "item_type", nullable = false, length = 30)
    private String itemType;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    protected Item() {}

    public Item(
            Room room,
            String name,
            String description,
            String imageUrl,
            String itemType
    ) {
        this.room = room;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.itemType = itemType;
    }
}
