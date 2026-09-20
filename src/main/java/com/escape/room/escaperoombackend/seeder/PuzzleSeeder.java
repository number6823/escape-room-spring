package com.escape.room.escaperoombackend.seeder;

import com.escape.room.escaperoombackend.domain.puzzle.Puzzle;
import com.escape.room.escaperoombackend.domain.room.Room;
import com.escape.room.escaperoombackend.repository.puzzle.PuzzleRepository;
import com.escape.room.escaperoombackend.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PuzzleSeeder implements CommandLineRunner {
    private final PuzzleRepository puzzleRepository;
    private final RoomRepository roomRepository;

    @Override
    public void run(String... args) {
        if (puzzleRepository.count() > 0) {
            return;
        }

        List<Room> rooms = roomRepository.findAllByOrderByRoomOrderAsc();

        if (rooms.size() < 3) {
            throw new IllegalStateException("퍼즐을 생성하려면 최소 3개의 방이 필요합니다.");
        }

        Room entrance = rooms.get(0);
        Room stduy = rooms.get(1);
        Room secondFloorHallway = rooms.get(2);

        List<Puzzle> puzzles = List.of(
                new Puzzle(
                        entrance,
                        "멈춰버린 시계",
                        "거실에 멈춰 있는 시계가 가리키는 시간을 확인한다.",
                        "TIME",
                        "1015",
                        1,
                        true
                ),
                new Puzzle(
                        stduy,
                        "가족사진의 시간",
                        "가족사진을 시간순으로 배열하여 숨겨진 숫자를 찾아낸다.",
                        "ORDER",
                        "7391",
                        1,
                        true
                ),
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
        puzzleRepository.saveAll(puzzles);
    }

}
