package com.escape.room.escaperoombackend.seeder;

import com.escape.room.escaperoombackend.domain.hint.Hint;
import com.escape.room.escaperoombackend.domain.puzzle.Puzzle;
import com.escape.room.escaperoombackend.domain.room.Room;
import com.escape.room.escaperoombackend.repository.hint.HintRepository;
import com.escape.room.escaperoombackend.repository.puzzle.PuzzleRepository;
import com.escape.room.escaperoombackend.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HintSeeder implements CommandLineRunner {

    private final RoomRepository roomRepository;
    private final PuzzleRepository puzzleRepository;
    private final HintRepository hintRepository;

    @Override
    public void run(String... args) {

        List<Room> rooms =
                roomRepository.findAllByOrderByRoomOrderAsc();

        if (rooms.isEmpty()) {
            return;
        }

        Room firstRoom = rooms.get(0);

        Puzzle firstPuzzle = puzzleRepository
                .findAllByRoom_IdOrderByPuzzleOrderAsc(firstRoom.getId())
                .stream()
                .filter(Puzzle::isRequired)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "첫 번째 퍼즐을 찾을 수 없습니다."
                        )
                );

        createHint(
                firstPuzzle,
                1,
                "시계가 멈춘 시간에 주목해보세요.",
                30
        );

        createHint(
                firstPuzzle,
                2,
                "시계의 시침과 분침이 가리키는 숫자를 확인해보세요.",
                60
        );

        createHint(
                firstPuzzle,
                3,
                "시계가 가리키는 시간은 10시 15분입니다.",
                120
        );
    }

    private void createHint(
            Puzzle puzzle,
            Integer hintLevel,
            String content,
            Integer penaltySeconds
    ) {

        boolean exists =
                hintRepository.existsByPuzzleIdAndHintLevel(
                        puzzle.getId(),
                        hintLevel
                );

        if (exists) {
            return;
        }

        Hint hint = new Hint(
                puzzle,
                hintLevel,
                content,
                penaltySeconds
        );

        hintRepository.save(hint);
    }
}