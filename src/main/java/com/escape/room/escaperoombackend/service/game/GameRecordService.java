package com.escape.room.escaperoombackend.service.game;

import com.escape.room.escaperoombackend.domain.game.GameRecord;
import com.escape.room.escaperoombackend.domain.user.User;
import com.escape.room.escaperoombackend.dto.game.response.GameRecordResponse;
import com.escape.room.escaperoombackend.dto.solvedpuzzle.response.SolvedPuzzleResponse;
import com.escape.room.escaperoombackend.exception.NotFoundException;
import com.escape.room.escaperoombackend.repository.game.GameRecordRepository;
import com.escape.room.escaperoombackend.repository.solvedpuzzle.SolvedPuzzleRepository;
import com.escape.room.escaperoombackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameRecordService {

    private final GameRecordRepository gameRecordRepository;
    private final UserRepository userRepository;
    private final GameProgressService gameProgressService;
    private final SolvedPuzzleRepository solvedPuzzleRepository;

    @Transactional
    public GameRecordResponse startGame(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "존재하지 않는 사용자입니다."
                        )
                );

        GameRecord gameRecord = new GameRecord(
                user,
                LocalDateTime.now(),
                "IN_PROGRESS"
        );

        GameRecord savedGameRecord =
                gameRecordRepository.save(gameRecord);

        gameProgressService.initializeProgress(savedGameRecord);

        return new GameRecordResponse(savedGameRecord);
    }

    @Transactional(readOnly = true)
    public List<GameRecordResponse> getMyGames(String email) {

        return gameRecordRepository
                .findAllByUser_EmailOrderByStartedAtDesc(email)
                .stream()
                .map(GameRecordResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public GameRecordResponse getGame(
            Long gameRecordId,
            String email
    ) {

        GameRecord gameRecord =
                gameRecordRepository
                        .findByIdAndUser_Email(
                                gameRecordId,
                                email
                        )
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "게임 기록을 찾을 수 없습니다."
                                )
                        );

        return new GameRecordResponse(gameRecord);
    }

    @Transactional(readOnly = true)
    public GameRecordResponse getInProgressGame(
            String email
    ) {

        GameRecord gameRecord =
                gameRecordRepository
                        .findFirstByUser_EmailAndClearStatusOrderByStartedAtDesc(
                                email,
                                "IN_PROGRESS"
                        )
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "진행 중인 게임이 없습니다."
                                )
                        );

        return new GameRecordResponse(gameRecord);
    }

    @Transactional(readOnly = true)
    public List<SolvedPuzzleResponse> getSolvedPuzzles(
            Long gameRecordId,
            String email
    ) {

        gameRecordRepository
                .findByIdAndUser_Email(
                        gameRecordId,
                        email
                )
                .orElseThrow(() ->
                        new NotFoundException(
                                "게임 기록을 찾을 수 없습니다."
                        )
                );

        return solvedPuzzleRepository
                .findAllByGameRecordIdOrderBySolvedAtAsc(gameRecordId)
                .stream()
                .map(SolvedPuzzleResponse::new)
                .toList();
    }
}