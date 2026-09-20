package com.escape.room.escaperoombackend.seeder;

import com.escape.room.escaperoombackend.domain.room.Room;
import com.escape.room.escaperoombackend.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomSeeder implements CommandLineRunner {

    private final RoomRepository roomRepository;

    @Override
    public void run(String... args) {

        if (roomRepository.count() > 0) {
            return;
        }

        List<Room> rooms = List.of(
                new Room(
                        "입구 / 거실",
                        "오래된 가족 저택의 입구와 거실.",
                        1,
                        null,
                        false
                ),
                new Room(
                        "서재",
                        "가족의 흔적과 오래된 기록이 남아 있는 서재.",
                        2,
                        null,
                        true
                ),
                new Room(
                        "2층 복도",
                        "기억과 관련된 단서가 남아 있는 2층 복도.",
                        3,
                        null,
                        true
                ),
                new Room(
                        "잠긴 방",
                        "아직 열리지 않은 가족의 비밀이 숨겨진 방",
                        4,
                        null,
                        true
                ),
                new Room(
                        "지하 연구실",
                        "가족의 실험과 관련된 진실이 숨겨진 지하 연구실",
                        5,
                        null,
                        true
                )
        );

        roomRepository.saveAll(rooms);
    }
}
