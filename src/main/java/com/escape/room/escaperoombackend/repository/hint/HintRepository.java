package com.escape.room.escaperoombackend.repository.hint;

import com.escape.room.escaperoombackend.domain.hint.Hint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HintRepository extends JpaRepository<Hint, Long> {

    List<Hint> findAllByPuzzle_IdOrderByHintLevelAsc(Long puzzleId);

    boolean existsByPuzzleIdAndHintLevel(Long puzzleId, Integer hintLevel);
}
