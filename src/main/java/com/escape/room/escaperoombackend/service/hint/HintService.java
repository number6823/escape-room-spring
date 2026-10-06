package com.escape.room.escaperoombackend.service.hint;

import com.escape.room.escaperoombackend.domain.game.GameRecord;
import com.escape.room.escaperoombackend.domain.game.GameProgress;
import com.escape.room.escaperoombackend.domain.hint.Hint;
import com.escape.room.escaperoombackend.domain.usedhint.UsedHint;
import com.escape.room.escaperoombackend.dto.hint.response.HintResponse;
import com.escape.room.escaperoombackend.dto.hint.response.UseHintResponse;
import com.escape.room.escaperoombackend.dto.hint.response.UsedHintResponse;
import com.escape.room.escaperoombackend.exception.NotFoundException;
import com.escape.room.escaperoombackend.repository.game.GameRecordRepository;
import com.escape.room.escaperoombackend.repository.game.GameProgressRepository;
import com.escape.room.escaperoombackend.repository.hint.HintRepository;
import com.escape.room.escaperoombackend.repository.hint.UsedHintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HintService {

    private final HintRepository hintRepository;
    private final GameRecordRepository gameRecordRepository;
    private final GameProgressRepository gameProgressRepository;
    private final UsedHintRepository usedHintRepository;

    public List<HintResponse> getHints(Long puzzleId) {

        return hintRepository
                .findAllByPuzzle_IdOrderByHintLevelAsc(puzzleId)
                .stream()
                .map(HintResponse::new)
                .toList();
    }

    public HintResponse getHint(Long hintId) {

        Hint hint = hintRepository
                .findById(hintId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "힌트를 찾을 수 없습니다."
                        )
                );

        return new HintResponse(hint);
    }

    public List<UsedHintResponse> getUsedHints(
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

        return usedHintRepository
                .findAllByGameRecordIdOrderByUsedAtAsc(gameRecordId)
                .stream()
                .map(UsedHintResponse::new)
                .toList();
    }

    @Transactional
    public UseHintResponse useHint(
            Long hintId,
            Long gameRecordId,
            String email
    ) {

        GameRecord gameRecord = gameRecordRepository
                .findByIdAndUser_Email(
                        gameRecordId,
                        email
                )
                .orElseThrow(() ->
                        new NotFoundException(
                                "게임 기록을 찾을 수 없습니다."
                        )
                );

        Hint hint = hintRepository
                .findById(hintId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "힌트를 찾을 수 없습니다."
                        )
                );

        boolean alreadyUsed =
                usedHintRepository
                        .existsByGameRecordIdAndHintId(
                                gameRecordId,
                                hintId
                        );

        if (alreadyUsed) {
            throw new IllegalArgumentException(
                    "이미 사용한 힌트입니다."
            );
        }

        List<GameProgress> progressList =
                gameProgressRepository
                        .findAllByGameRecordId(gameRecordId);

        GameProgress currentProgress =
                progressList.stream()
                        .filter(progress -> !progress.isCleared())
                        .filter(progress ->
                                progress.getCurrentPuzzle() != null
                        )
                        .findFirst()
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "현재 진행 중인 퍼즐이 없습니다."
                                )
                        );

        if (!currentProgress
                .getCurrentPuzzle()
                .getId()
                .equals(hint.getPuzzle().getId())) {

            throw new IllegalArgumentException(
                    "현재 진행 중인 퍼즐의 힌트가 아닙니다."
            );
        }

        UsedHint usedHint = new UsedHint(
                gameRecord,
                hint,
                LocalDateTime.now()
        );

        usedHintRepository.save(usedHint);

        gameRecord.increaseHintCount();

        return new UseHintResponse(
                hint.getId(),
                hint.getHintLevel(),
                hint.getContent(),
                hint.getPenaltySeconds()
        );
    }
}