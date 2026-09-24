package com.escape.room.escaperoombackend.repository.solvedpuzzle;

import com.escape.room.escaperoombackend.domain.solvedpuzzle.SolvedPuzzle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolvedPuzzleRepository extends JpaRepository<SolvedPuzzle, Long> {

    boolean existsByGameRecordIdAndPuzzleId(
            Long gameRecordId,
            Long puzzleId
    );

    List<SolvedPuzzle> findAllByGameRecordId(Long gameRecordId);
}