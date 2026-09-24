package com.escape.room.escaperoombackend.dto.item.response;

import com.escape.room.escaperoombackend.domain.item.Item;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ItemResponse {
    private final Long id;
    private final Long roomId;
    private final String name;
    private final String description;
    private final String imageUrl;
    private final String itemType;
    private final LocalDateTime createdAt;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.roomId = item.getRoom().getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.imageUrl = item.getImageUrl();
        this.itemType = item.getItemType();
        this.createdAt = item.getCreatedAt();
    }
}
