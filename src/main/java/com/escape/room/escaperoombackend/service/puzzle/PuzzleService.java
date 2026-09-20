package com.escape.room.escaperoombackend.service.puzzle;

import com.escape.room.escaperoombackend.dto.puzzle.response.PuzzleResponse;
import com.escape.room.escaperoombackend.repository.puzzle.PuzzleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PuzzleService {

    private final PuzzleRepository puzzleRepository;

    @Transactional(readOnly = true)
    public List<PuzzleResponse> getPuzzles(Long roomId) {

        return puzzleRepository.findAllByRoom_IdOrderByPuzzleOrderAsc(roomId)
                .stream()
                .map(PuzzleResponse::new)
                .toList();
    }
}