package com.escape.room.escaperoombackend.repository.puzzle;

import com.escape.room.escaperoombackend.domain.puzzle.PuzzleAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PuzzleAttemptRepository extends JpaRepository<PuzzleAttempt, Long> {
    List<PuzzleAttempt> findAllByGameRecordIdAndPuzzleIdOrderByAttemptedAtAsc(
            Long gameRecordId,
            Long puzzleId
    );

    long countByGameRecordIdAndPuzzleId(
            Long gameRecordId,
            Long puzzleId
    );
}
