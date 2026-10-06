package com.escape.room.escaperoombackend.service.game;

import com.escape.room.escaperoombackend.domain.game.GameProgress;
import com.escape.room.escaperoombackend.domain.game.GameRecord;
import com.escape.room.escaperoombackend.domain.puzzle.Puzzle;
import com.escape.room.escaperoombackend.domain.room.Room;
import com.escape.room.escaperoombackend.dto.game.response.GameProgressResponse;
import com.escape.room.escaperoombackend.exception.NotFoundException;
import com.escape.room.escaperoombackend.repository.game.GameProgressRepository;
import com.escape.room.escaperoombackend.repository.game.GameRecordRepository;
import com.escape.room.escaperoombackend.repository.hint.UsedHintRepository;
import com.escape.room.escaperoombackend.repository.puzzle.PuzzleRepository;
import com.escape.room.escaperoombackend.repository.room.RoomRepository;
import com.escape.room.escaperoombackend.repository.solvedpuzzle.SolvedPuzzleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GameProgressService {

    private final GameProgressRepository gameProgressRepository;
    private final GameRecordRepository gameRecordRepository;
    private final RoomRepository roomRepository;
    private final PuzzleRepository puzzleRepository;
    private final SolvedPuzzleRepository solvedPuzzleRepository;
    private final UsedHintRepository usedHintRepository;

    public void initializeProgress(GameRecord gameRecord) {

        List<Room> rooms = roomRepository.findAllByOrderByRoomOrderAsc();

        if (rooms.isEmpty()) {
            throw new IllegalArgumentException("방이 존재하지 않습니다.");
        }

        Room firstRoom = rooms.get(0);

        boolean alreadyExists =
                gameProgressRepository
                        .findByGameRecordIdAndRoomId(
                                gameRecord.getId(),
                                firstRoom.getId()
                        )
                        .isPresent();

        if (alreadyExists) {
            return;
        }

        Puzzle firstPuzzle = puzzleRepository
                .findAllByRoom_IdOrderByPuzzleOrderAsc(firstRoom.getId())
                .stream()
                .filter(Puzzle::isRequired)
                .findFirst()
                .orElse(null);

        GameProgress progress = new GameProgress(
                gameRecord,
                firstRoom,
                firstPuzzle
        );

        gameProgressRepository.save(progress);
    }

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
                                new NotFoundException(
                                        "현재 방의 진행 정보를 찾을 수 없습니다."
                                )
                        );

        List<Puzzle> puzzles =
                puzzleRepository.findAllByRoom_IdOrderByPuzzleOrderAsc(
                        currentRoom.getId()
                );

        Set<Long> solvedPuzzleIds =
                solvedPuzzleRepository
                        .findAllByGameRecordId(gameRecord.getId())
                        .stream()
                        .map(solved -> solved.getPuzzle().getId())
                        .collect(Collectors.toSet());

        Puzzle nextPuzzle = puzzles.stream()
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

        unlockNextRoom(
                gameRecord,
                currentRoom
        );
    }

    private void unlockNextRoom(
            GameRecord gameRecord,
            Room currentRoom
    ) {

        List<Room> rooms =
                roomRepository.findAllByOrderByRoomOrderAsc();

        int currentIndex = -1;

        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getId().equals(currentRoom.getId())) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex == -1) {
            throw new NotFoundException(
                    "현재 방 정보를 찾을 수 없습니다."
            );
        }

        boolean isLastRoom =
                currentIndex == rooms.size() - 1;

        if (isLastRoom) {

            int totalPenaltySeconds =
                    usedHintRepository
                            .findAllByGameRecordId(gameRecord.getId())
                            .stream()
                            .mapToInt(
                                    usedHint ->
                                            usedHint
                                                    .getHint()
                                                    .getPenaltySeconds()
                            )
                            .sum();

            gameRecord.complete(totalPenaltySeconds);

            return;
        }

        Room nextRoom = rooms.get(currentIndex + 1);

        Puzzle firstPuzzle =
                puzzleRepository
                        .findAllByRoom_IdOrderByPuzzleOrderAsc(
                                nextRoom.getId()
                        )
                        .stream()
                        .filter(Puzzle::isRequired)
                        .findFirst()
                        .orElse(null);

        GameProgress nextProgress =
                new GameProgress(
                        gameRecord,
                        nextRoom,
                        firstPuzzle
                );

        gameProgressRepository.save(nextProgress);
    }

    @Transactional(readOnly = true)
    public List<GameProgressResponse> getProgress(
            Long gameRecordId,
            String email
    ) {

        gameRecordRepository
                .findByIdAndUser_Email(gameRecordId, email)
                .orElseThrow(() ->
                        new NotFoundException(
                                "게임 기록을 찾을 수 없습니다."
                        )
                );

        return gameProgressRepository
                .findAllByGameRecordId(gameRecordId)
                .stream()
                .sorted(
                        (a, b) ->
                                Integer.compare(
                                        a.getRoom().getRoomOrder(),
                                        b.getRoom().getRoomOrder()
                                )
                )
                .map(GameProgressResponse::new)
                .toList();
    }
}