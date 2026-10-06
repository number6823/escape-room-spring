package com.escape.room.escaperoombackend.repository.game;

import com.escape.room.escaperoombackend.domain.game.GameRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRecordRepository extends JpaRepository<GameRecord, Long> {

    List<GameRecord> findAllByUser_IdOrderByStartedAtDesc(Long userId);

    Optional<GameRecord> findByIdAndUser_Email(
            Long gameRecordId,
            String email
    );

    List<GameRecord> findAllByUser_EmailOrderByStartedAtDesc(
            String email
    );

    Optional<GameRecord> findFirstByUser_EmailAndClearStatusOrderByStartedAtDesc(
            String email,
            String clearStatus
    );
}