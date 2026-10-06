package com.escape.room.escaperoombackend.repository.hint;

import com.escape.room.escaperoombackend.domain.usedhint.UsedHint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsedHintRepository extends JpaRepository<UsedHint, Long> {

    boolean existsByGameRecordIdAndHintId(
            Long gameRecordId,
            Long hintId
    );

    List<UsedHint> findAllByGameRecordId(Long gameRecordId);

    List<UsedHint> findAllByGameRecordIdOrderByUsedAtAsc(
            Long gameRecordId
    );

    long countByGameRecordIdAndHint_Puzzle_Id(
            Long gameRecordId,
            Long puzzleId
    );
}
