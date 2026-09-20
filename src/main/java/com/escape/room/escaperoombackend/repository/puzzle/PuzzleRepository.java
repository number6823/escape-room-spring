package com.escape.room.escaperoombackend.repository.puzzle;

import com.escape.room.escaperoombackend.domain.puzzle.Puzzle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PuzzleRepository extends JpaRepository<Puzzle,Long> {

    List<Puzzle> findAllByRoom_IdOrderByPuzzleOrderAsc(Long roomId);

}
