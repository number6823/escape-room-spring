package com.escape.room.escaperoombackend.service.puzzle;

import com.escape.room.escaperoombackend.domain.gamerecord.GameRecord;
import com.escape.room.escaperoombackend.domain.puzzle.Puzzle;
import com.escape.room.escaperoombackend.domain.solvedpuzzle.SolvedPuzzle;
import com.escape.room.escaperoombackend.dto.puzzle.response.PuzzleResponse;
import com.escape.room.escaperoombackend.dto.solvedpuzzle.request.SolvePuzzleRequest;
import com.escape.room.escaperoombackend.dto.solvedpuzzle.response.SolvePuzzleResponse;
import com.escape.room.escaperoombackend.repository.gamerecord.GameRecordRepository;
import com.escape.room.escaperoombackend.repository.puzzle.PuzzleRepository;
import com.escape.room.escaperoombackend.repository.solvedpuzzle.SolvedPuzzleRepository;
import com.escape.room.escaperoombackend.service.gameprogress.GameProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PuzzleService {

    private final PuzzleRepository puzzleRepository;
    private final GameRecordRepository gameRecordRepository;
    private final SolvedPuzzleRepository solvedPuzzleRepository;
    private final GameProgressService gameProgressService;

    @Transactional(readOnly = true)
    public List<PuzzleResponse> getPuzzles(Long roomId) {

        return puzzleRepository.findAllByRoom_IdOrderByPuzzleOrderAsc(roomId)
                .stream()
                .map(PuzzleResponse::new)
                .toList();
    }

    @Transactional
    public SolvePuzzleResponse solvePuzzle(
            Long gameRecordId,
            Long puzzleId,
            SolvePuzzleRequest request,
            String email
    ) {

        GameRecord gameRecord = gameRecordRepository
                .findByIdAndUser_Email(gameRecordId, email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "존재하지 않는 게임 기록입니다."
                        )
                );

        Puzzle puzzle = puzzleRepository.findById(puzzleId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "존재하지 않는 퍼즐입니다."
                        )
                );

        if (solvedPuzzleRepository
                .existsByGameRecordIdAndPuzzleId(
                        gameRecordId,
                        puzzleId
                )) {

            return new SolvePuzzleResponse(
                    puzzleId,
                    true,
                    "이미 해결한 퍼즐입니다."
            );
        }

        if (!puzzle.getAnswer().equals(request.getAnswer())) {

            return new SolvePuzzleResponse(
                    puzzleId,
                    false,
                    "정답이 아닙니다."
            );
        }

        SolvedPuzzle solvedPuzzle = new SolvedPuzzle(
                gameRecord,
                puzzle,
                LocalDateTime.now(),
                1,
                0
        );

        solvedPuzzleRepository.save(solvedPuzzle);

        gameProgressService.updateAfterSolve(
                gameRecord,
                puzzle
        );

        return new SolvePuzzleResponse(
                puzzleId,
                true,
                "퍼즐을 해결했습니다."
        );
    }
}