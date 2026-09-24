package com.escape.room.escaperoombackend.controller.puzzle;

import com.escape.room.escaperoombackend.dto.puzzle.response.PuzzleResponse;
import com.escape.room.escaperoombackend.dto.solvedpuzzle.request.SolvePuzzleRequest;
import com.escape.room.escaperoombackend.dto.solvedpuzzle.response.SolvePuzzleResponse;
import com.escape.room.escaperoombackend.service.puzzle.PuzzleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms/{roomId}/puzzles")
public class PuzzleController {

    private final PuzzleService puzzleService;

    @GetMapping
    public List<PuzzleResponse> getPuzzles(
            @PathVariable Long roomId
    ) {
        return puzzleService.getPuzzles(roomId);
    }

    @PostMapping("/{puzzleId}/solve")
    public SolvePuzzleResponse solvePuzzle(
            @PathVariable Long puzzleId,
            @Valid @RequestBody SolvePuzzleRequest request,
            @RequestParam Long gameRecordId,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return puzzleService.solvePuzzle(
                gameRecordId,
                puzzleId,
                request,
                email
        );
    }
}