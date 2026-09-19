package com.escape.room.escaperoombackend.repository.room;

import com.escape.room.escaperoombackend.domain.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByOrderByRoomOrderAsc();
}