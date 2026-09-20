package com.escape.room.escaperoombackend.controller;


import com.escape.room.escaperoombackend.dto.puzzle.response.PuzzleResponse;
import com.escape.room.escaperoombackend.service.puzzle.PuzzleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms/{roomId}/puzzles")
public class PuzzleController {

    private final PuzzleService puzzleService;

    @GetMapping
    public List<PuzzleResponse> getPuzzles(
            @PathVariable Long roomId
    )  {
        return puzzleService.getPuzzles(roomId);
    }
}
