package com.escape.room.escaperoombackend.repository.item;

import com.escape.room.escaperoombackend.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByRoom_IdOrderByIdAsc(Long roomId);

    boolean existsByRoomIdAndName(Long roomId, String name);
}
