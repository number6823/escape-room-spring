package com.escape.room.escaperoombackend.repository.gamerecord;

import com.escape.room.escaperoombackend.domain.gamerecord.GameRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRecordRepository extends JpaRepository<GameRecord, Long> {

    List<GameRecord> findAllByUser_IdOrderByStartedAtDesc(Long userId);

    Optional<GameRecord> findByIdAndUser_Email(Long gameRecordId, String email);
}