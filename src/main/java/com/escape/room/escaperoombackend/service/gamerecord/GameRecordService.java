package com.escape.room.escaperoombackend.service.gamerecord;

import com.escape.room.escaperoombackend.domain.gamerecord.GameRecord;
import com.escape.room.escaperoombackend.domain.user.User;
import com.escape.room.escaperoombackend.dto.gamerecord.response.GameRecordResponse;
import com.escape.room.escaperoombackend.repository.gamerecord.GameRecordRepository;
import com.escape.room.escaperoombackend.repository.user.UserRepository;
import com.escape.room.escaperoombackend.service.gameprogress.GameProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GameRecordService {

    private final GameRecordRepository gameRecordRepository;
    private final UserRepository userRepository;
    private final GameProgressService gameProgressService;

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
}