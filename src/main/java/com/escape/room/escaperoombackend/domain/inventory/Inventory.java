package com.escape.room.escaperoombackend.domain.inventory;

import com.escape.room.escaperoombackend.domain.item.Item;
import com.escape.room.escaperoombackend.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "inventories",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_inventory_user_item",
                        columnNames = {"user_id", "item_id"}
                )
        }
)
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "obtained_at", nullable = false)
    private LocalDateTime obtainedAt;

    protected Inventory() {}

    public Inventory(
            User user,
            Item item,
            LocalDateTime obtainedAt
    ) {
        this.user = user;
        this.item = item;
        this.obtainedAt = obtainedAt;
    }
}
