package com.escape.room.escaperoombackend.service.gameprogress;

import com.escape.room.escaperoombackend.domain.gameprogress.GameProgress;
import com.escape.room.escaperoombackend.domain.gamerecord.GameRecord;
import com.escape.room.escaperoombackend.domain.puzzle.Puzzle;
import com.escape.room.escaperoombackend.domain.room.Room;
import com.escape.room.escaperoombackend.dto.gameprogress.response.GameProgressResponse;
import com.escape.room.escaperoombackend.repository.gameprogress.GameProgressRepository;
import com.escape.room.escaperoombackend.repository.gamerecord.GameRecordRepository;
import com.escape.room.escaperoombackend.repository.puzzle.PuzzleRepository;
import com.escape.room.escaperoombackend.repository.room.RoomRepository;
import com.escape.room.escaperoombackend.repository.solvedpuzzle.SolvedPuzzleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameProgressService {

    private final GameProgressRepository gameProgressRepository;
    private final GameRecordRepository gameRecordRepository;
    private final PuzzleRepository puzzleRepository;
    private final RoomRepository roomRepository;
    private final SolvedPuzzleRepository solvedPuzzleRepository;

    @Transactional
    public void initializeProgress(GameRecord gameRecord) {

        Room firstRoom = roomRepository.findAllByOrderByRoomOrderAsc()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("게임에 등록된 방이 없습니다.")
                );

        if (gameProgressRepository
                .findByGameRecordIdAndRoomId(
                        gameRecord.getId(),
                        firstRoom.getId()
                )
                .isPresent()) {
            return;
        }

        Puzzle firstPuzzle = puzzleRepository
                .findAllByRoom_IdOrderByPuzzleOrderAsc(firstRoom.getId())
                .stream()
                .filter(Puzzle::isRequired)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "첫 번째 방에 필수 퍼즐이 없습니다."
                        )
                );

        GameProgress progress =
                new GameProgress(
                        gameRecord,
                        firstRoom,
                        firstPuzzle
                );

        gameProgressRepository.save(progress);
    }

    @Transactional
    public void updateAfterSolve(
            GameRecord gameRecord,
            Puzzle solvedPuzzle
    ) {

        Room currentRoom = solvedPuzzle.getRoom();

        GameProgress currentProgress =
                gameProgressRepository
                        .findByGameRecordIdAndRoomId(
                                gameRecord.getId(),
                                currentRoom.getId()
                        )
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "현재 방의 진행 기록이 없습니다."
                                )
                        );

        List<Puzzle> roomPuzzles =
                puzzleRepository
                        .findAllByRoom_IdOrderByPuzzleOrderAsc(
                                currentRoom.getId()
                        );

        Set<Long> solvedPuzzleIds =
                solvedPuzzleRepository
                        .findAllByGameRecordId(gameRecord.getId())
                        .stream()
                        .map(solved -> solved.getPuzzle().getId())
                        .collect(Collectors.toSet());

        Puzzle nextPuzzle = roomPuzzles.stream()
                .filter(Puzzle::isRequired)
                .filter(puzzle ->
                        !solvedPuzzleIds.contains(puzzle.getId())
                )
                .findFirst()
                .orElse(null);

        if (nextPuzzle != null) {
            currentProgress.updateCurrentPuzzle(nextPuzzle);
            return;
        }

        currentProgress.clear();

        unlockNextRoom(gameRecord, currentRoom);
    }

    private void unlockNextRoom(
            GameRecord gameRecord,
            Room currentRoom
    ) {

        Room nextRoom = roomRepository
                .findAllByOrderByRoomOrderAsc()
                .stream()
                .filter(room ->
                        room.getRoomOrder() > currentRoom.getRoomOrder()
                )
                .findFirst()
                .orElse(null);

        if (nextRoom == null) {
            gameRecord.complete();
            return;
        }

        if (gameProgressRepository
                .findByGameRecordIdAndRoomId(
                        gameRecord.getId(),
                        nextRoom.getId()
                )
                .isPresent()) {
            return;
        }

        Puzzle firstPuzzleOfNextRoom =
                puzzleRepository
                        .findAllByRoom_IdOrderByPuzzleOrderAsc(
                                nextRoom.getId()
                        )
                        .stream()
                        .filter(Puzzle::isRequired)
                        .findFirst()
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "다음 방에 필수 퍼즐이 없습니다."
                                )
                        );

        GameProgress nextProgress =
                new GameProgress(
                        gameRecord,
                        nextRoom,
                        firstPuzzleOfNextRoom
                );

        gameProgressRepository.save(nextProgress);
    }

    @Transactional(readOnly = true)
    public List<GameProgressResponse> getProgress(
            Long gameRecordId,
            String email
    ) {

        gameRecordRepository
                .findByIdAndUser_Email(
                        gameRecordId,
                        email
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "존재하지 않는 게임 기록입니다."
                        )
                );

        return gameProgressRepository
                .findAllByGameRecordId(gameRecordId)
                .stream()
                .sorted(
                        Comparator.comparing(
                                progress ->
                                        progress.getRoom().getRoomOrder()
                        )
                )
                .map(GameProgressResponse::new)
                .toList();
    }
}