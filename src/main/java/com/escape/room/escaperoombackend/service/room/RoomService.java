package com.escape.room.escaperoombackend.service.room;

import com.escape.room.escaperoombackend.domain.room.Room;
import com.escape.room.escaperoombackend.dto.room.response.RoomResponse;
import com.escape.room.escaperoombackend.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public List<RoomResponse> getRooms() {
        return roomRepository.findAllByOrderByRoomOrderAsc()
                .stream()
                .map(RoomResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoomResponse getRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));

        return new RoomResponse(room);
    }
}
