package com.escape.room.escaperoombackend.seeder;

import com.escape.room.escaperoombackend.domain.item.Item;
import com.escape.room.escaperoombackend.domain.room.Room;
import com.escape.room.escaperoombackend.repository.item.ItemRepository;
import com.escape.room.escaperoombackend.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemSeeder implements CommandLineRunner {

    private final ItemRepository itemRepository;
    private final RoomRepository roomRepository;

    @Override
    public void run(String... args) {

        List<Room> rooms = roomRepository.findAllByOrderByRoomOrderAsc();

        if (rooms.size() < 5) {
            throw new IllegalStateException(
                    "아이템을 생성하려면 5개의 방이 필요합니다."
            );
        }

        Room entrance = rooms.get(0);
        Room study = rooms.get(1);
        Room secondFloorHallway = rooms.get(2);
        Room lockedRoom = rooms.get(3);
        Room researchLab = rooms.get(4);

        List<Item> items = new ArrayList<>();

        addItem(
                items,
                entrance,
                "어린 시절의 장난감",
                "주인공의 어린 시절을 떠올리게 하는 오래된 물건이다.",
                null,
                "CHILDHOOD_OBJECT"
        );

        addItem(
                items,
                study,
                "오래된 사진첩",
                "가족의 모습을 기록한 오래된 사진첩이다.",
                null,
                "PHOTO_ALBUM"
        );

        addItem(
                items,
                secondFloorHallway,
                "기억의 조각",
                "기억과 관련된 기록이 남아 있는 작은 물건이다.",
                null,
                "MEMORY"
        );

        addItem(
                items,
                lockedRoom,
                "찢어진 편지 조각",
                "복원되지 않은 편지의 일부다.",
                null,
                "LETTER_FRAGMENT"
        );

        addItem(
                items,
                researchLab,
                "연구 기록 수첩",
                "가족의 실험과 관련된 기록이 남아 있는 수첩이다.",
                null,
                "RESEARCH_RECORD"
        );

        if (!items.isEmpty()) {
            itemRepository.saveAll(items);
        }
    }

    private void addItem(
            List<Item> items,
            Room room,
            String name,
            String description,
            String imageUrl,
            String itemType
    ) {

        if (itemRepository.existsByRoomIdAndName(
                room.getId(),
                name
        )) {
            return;
        }

        items.add(
                new Item(
                        room,
                        name,
                        description,
                        imageUrl,
                        itemType
                )
        );
    }
}