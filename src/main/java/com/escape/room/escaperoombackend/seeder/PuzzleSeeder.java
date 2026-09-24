package com.escape.room.escaperoombackend.seeder;

import com.escape.room.escaperoombackend.domain.puzzle.Puzzle;
import com.escape.room.escaperoombackend.domain.room.Room;
import com.escape.room.escaperoombackend.repository.puzzle.PuzzleRepository;
import com.escape.room.escaperoombackend.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PuzzleSeeder implements CommandLineRunner {

    private final PuzzleRepository puzzleRepository;
    private final RoomRepository roomRepository;

    @Override
    public void run(String... args) {

        List<Room> rooms = roomRepository.findAllByOrderByRoomOrderAsc();

        if (rooms.size() < 5) {
            throw new IllegalStateException(
                    "퍼즐을 생성하려면 5개의 방이 필요합니다."
            );
        }

        List<Puzzle> puzzles = new ArrayList<>();

        Room entrance = rooms.get(0);
        Room study = rooms.get(1);
        Room secondFloorHallway = rooms.get(2);
        Room lockedRoom = rooms.get(3);
        Room researchLab = rooms.get(4);

        if (!puzzleRepository.existsByRoomIdAndPuzzleOrder(
                entrance.getId(), 1
        )) {
            puzzles.add(
                    new Puzzle(
                            entrance,
                            "멈춰버린 시계",
                            "거실에 멈춰 있는 시계가 가리키는 시간을 확인한다.",
                            "TIME",
                            "1015",
                            1,
                            true
                    )
            );
        }

        if (!puzzleRepository.existsByRoomIdAndPuzzleOrder(
                study.getId(), 1
        )) {
            puzzles.add(
                    new Puzzle(
                            study,
                            "가족사진의 시간",
                            "가족사진을 시간순으로 배열하여 숨겨진 숫자를 찾아낸다.",
                            "ORDER",
                            "7391",
                            1,
                            true
                    )
            );
        }

        if (!puzzleRepository.existsByRoomIdAndPuzzleOrder(
                secondFloorHallway.getId(), 1
        )) {
            puzzles.add(
                    new Puzzle(
                            secondFloorHallway,
                            "세 개의 기억",
                            "세 개의 기억을 올바른 순서로 배열하여 다음 단서를 찾아낸다.",
                            "ORDER",
                            "482",
                            1,
                            true
                    )
            );
        }

        if (!puzzleRepository.existsByRoomIdAndPuzzleOrder(
                lockedRoom.getId(), 1
        )) {
            puzzles.add(
                    new Puzzle(
                            lockedRoom,
                            "찢어진 편지 복원",
                            "찢어진 편지를 올바른 순서로 복원해 어머니의 경고를 확인한다.",
                            "ORDER",
                            "2413",
                            1,
                            true
                    )
            );
        }

        if (!puzzleRepository.existsByRoomIdAndPuzzleOrder(
                researchLab.getId(), 1
        )) {
            puzzles.add(
                    new Puzzle(
                            researchLab,
                            "흩어진 기억의 결합",
                            "앞에서 얻은 숫자 단서를 결합해 마지막 문을 연다.",
                            "COMBINATION",
                            "510",
                            1,
                            true
                    )
            );
        }

        if (!puzzles.isEmpty()) {
            puzzleRepository.saveAll(puzzles);
        }
    }
}