package com.escape.room.escaperoombackend.dto.inventory.response;

import com.escape.room.escaperoombackend.domain.inventory.Inventory;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InventoryResponse {

    private final Long id;
    private final Long itemId;
    private final String itemName;
    private final String description;
    private final String imageUrl;
    private final String itemType;
    private final LocalDateTime obtainedAt;

    public InventoryResponse(Inventory inventory) {
        this.id = inventory.getId();
        this.itemId = inventory.getItem().getId();
        this.itemName = inventory.getItem().getName();
        this.description = inventory.getItem().getDescription();
        this.imageUrl = inventory.getItem().getImageUrl();
        this.itemType = inventory.getItem().getItemType();
        this.obtainedAt = inventory.getObtainedAt();
    }
}