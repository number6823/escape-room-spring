package com.escape.room.escaperoombackend.dto.room.response;

import com.escape.room.escaperoombackend.domain.room.Room;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RoomResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final Integer roomOrder;
    private final String backgroundImage;
    private final boolean isLocked;
    private final LocalDateTime createdAt;

    public RoomResponse(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.description = room.getDescription();
        this.roomOrder = room.getRoomOrder();
        this.backgroundImage = room.getBackgroundImage();
        this.isLocked = room.isLocked();
        this.createdAt = room.getCreatedAt();
    }
}
