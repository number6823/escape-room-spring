package com.escape.room.escaperoombackend.repository.game;

import com.escape.room.escaperoombackend.domain.game.GameProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameProgressRepository extends JpaRepository<GameProgress, Long> {

    Optional<GameProgress> findByGameRecordIdAndRoomId(
            Long gameRecordId,
            Long roomId
    );

    List<GameProgress> findAllByGameRecordId(Long gameRecordId);
}