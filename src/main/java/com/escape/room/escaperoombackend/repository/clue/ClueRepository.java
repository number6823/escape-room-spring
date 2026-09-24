package com.escape.room.escaperoombackend.repository.clue;

import com.escape.room.escaperoombackend.domain.clue.Clue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClueRepository extends JpaRepository<Clue, Long> {

    List<Clue> findAllByRoom_IdOrderByIdAsc(Long roomId);

    boolean existsByRoomIdAndName(Long roomId, String name);
}
