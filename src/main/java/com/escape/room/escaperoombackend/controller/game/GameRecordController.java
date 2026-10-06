package com.escape.room.escaperoombackend.controller.game;

import com.escape.room.escaperoombackend.dto.game.response.GameRecordResponse;
import com.escape.room.escaperoombackend.dto.solvedpuzzle.response.SolvedPuzzleResponse;
import com.escape.room.escaperoombackend.service.game.GameRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameRecordController {

    private final GameRecordService gameRecordService;

    @PostMapping
    public GameRecordResponse startGame(
            Authentication authentication
    ) {
        String email = authentication.getName();

        return gameRecordService.startGame(email);
    }

    @GetMapping
    public List<GameRecordResponse> getMyGames(
            Authentication authentication
    ) {
        String email = authentication.getName();

        return gameRecordService.getMyGames(email);
    }

    @GetMapping("/in-progress")
    public GameRecordResponse getInProgressGame(
            Authentication authentication
    ) {
        String email = authentication.getName();

        return gameRecordService.getInProgressGame(email);
    }

    @GetMapping("/{gameRecordId}")
    public GameRecordResponse getGame(
            @PathVariable Long gameRecordId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return gameRecordService.getGame(
                gameRecordId,
                email
        );
    }

    @GetMapping("/{gameRecordId}/solved-puzzles")
    public List<SolvedPuzzleResponse> getSolvedPuzzles(
            @PathVariable Long gameRecordId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return gameRecordService.getSolvedPuzzles(
                gameRecordId,
                email
        );
    }
}