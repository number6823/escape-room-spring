package com.escape.room.escaperoombackend.seeder;

import com.escape.room.escaperoombackend.domain.clue.Clue;
import com.escape.room.escaperoombackend.domain.puzzle.Puzzle;
import com.escape.room.escaperoombackend.domain.room.Room;
import com.escape.room.escaperoombackend.repository.clue.ClueRepository;
import com.escape.room.escaperoombackend.repository.puzzle.PuzzleRepository;
import com.escape.room.escaperoombackend.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClueSeeder implements CommandLineRunner {

    private final ClueRepository clueRepository;
    private final RoomRepository roomRepository;
    private final PuzzleRepository puzzleRepository;

    @Override
    public void run(String... args) {

        List<Room> rooms = roomRepository.findAllByOrderByRoomOrderAsc();

        if (rooms.size() < 5) {
            throw new IllegalStateException(
                    "단서를 생성하려면 5개의 방이 필요합니다."
            );
        }

        Room entrance = rooms.get(0);
        Room study = rooms.get(1);
        Room secondFloorHallway = rooms.get(2);
        Room lockedRoom = rooms.get(3);
        Room researchLab = rooms.get(4);

        Puzzle puzzle1 = getPuzzle(entrance.getId());
        Puzzle puzzle2 = getPuzzle(study.getId());
        Puzzle puzzle3 = getPuzzle(secondFloorHallway.getId());
        Puzzle puzzle4 = getPuzzle(lockedRoom.getId());
        Puzzle puzzle5 = getPuzzle(researchLab.getId());

        List<Clue> clues = new ArrayList<>();

        addClue(
                clues,
                entrance,
                puzzle1,
                "멈춰버린 시계",
                "거실의 시계는 10시 15분에서 멈춰 있다.",
                "CLOCK",
                null
        );

        addClue(
                clues,
                entrance,
                null,
                "어머니의 흔적",
                "서랍 안에서 오래된 사진과 어머니의 손글씨가 발견된다.",
                "PHOTO",
                null
        );

        addClue(
                clues,
                study,
                puzzle2,
                "가족사진",
                "가족사진에는 서로 다른 시기의 가족 모습이 남아 있다.",
                "PHOTO",
                null
        );

        addClue(
                clues,
                study,
                null,
                "어머니의 편지",
                "편지에는 주인공의 어린 시절과 관련된 익숙한 기억이 남아 있다.",
                "LETTER",
                null
        );

        addClue(
                clues,
                secondFloorHallway,
                puzzle3,
                "세 개의 기억 조각",
                "서로 다른 시점의 기억 세 조각이 흩어져 있다.",
                "MEMORY",
                null
        );

        addClue(
                clues,
                secondFloorHallway,
                null,
                "나는 하나가 아니었다",
                "기억의 조각 사이에서 자신을 여러 모습으로 인식했던 흔적이 발견된다.",
                "MEMORY",
                null
        );

        addClue(
                clues,
                lockedRoom,
                puzzle4,
                "찢어진 편지 조각",
                "찢어진 편지를 복원하면 아버지의 실험과 어머니의 경고가 드러난다.",
                "LETTER",
                null
        );

        addClue(
                clues,
                lockedRoom,
                null,
                "아버지의 실험 기록",
                "가족 저택에서 기억과 정신 상태를 조절하려 했던 실험 기록이 남아 있다.",
                "RESEARCH",
                null
        );

        addClue(
                clues,
                researchLab,
                puzzle5,
                "연구 기록",
                "앞에서 발견한 기억과 실험 기록이 서로 연결되어 있음을 보여주는 자료다.",
                "RESEARCH",
                null
        );

        addClue(
                clues,
                researchLab,
                null,
                "분리된 기억의 진실",
                "주인공이 서로 다른 자신을 별개의 사람으로 인식하게 된 이유를 보여주는 기록이다.",
                "RESEARCH",
                null
        );

        if (!clues.isEmpty()) {
            clueRepository.saveAll(clues);
        }
    }

    private Puzzle getPuzzle(Long roomId) {
        return puzzleRepository
                .findAllByRoom_IdOrderByPuzzleOrderAsc(roomId)
                .stream()
                .filter(Puzzle::isRequired)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "해당 방에 필수 퍼즐이 없습니다."
                        )
                );
    }

    private void addClue(
            List<Clue> clues,
            Room room,
            Puzzle puzzle,
            String name,
            String description,
            String clueType,
            String imageUrl
    ) {

        if (clueRepository.existsByRoomIdAndName(room.getId(), name)) {
            return;
        }

        clues.add(
                new Clue(
                        room,
                        puzzle,
                        name,
                        description,
                        clueType,
                        imageUrl
                )
        );
    }
}