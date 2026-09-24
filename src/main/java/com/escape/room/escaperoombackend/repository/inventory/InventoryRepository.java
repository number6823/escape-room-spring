package com.escape.room.escaperoombackend.repository.inventory;

import com.escape.room.escaperoombackend.domain.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findAllByUser_IdOrderByObtainedAtDesc(Long userId);

    Optional<Inventory> findByUserIdAndItemId(Long userId, Long itemId);

    boolean existsByUserIdAndItemId(Long userId, Long itemId);
}