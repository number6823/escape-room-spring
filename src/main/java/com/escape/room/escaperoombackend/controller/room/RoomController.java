package com.escape.room.escaperoombackend.controller.room;

import com.escape.room.escaperoombackend.dto.room.response.RoomResponse;
import com.escape.room.escaperoombackend.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public List<RoomResponse> getRooms() {
        return roomService.getRooms();
    }

    @GetMapping("/{roomId}")
    public RoomResponse getRoom(
            @PathVariable Long roomId
    ) {
        return roomService.getRoom(roomId);
    }
}