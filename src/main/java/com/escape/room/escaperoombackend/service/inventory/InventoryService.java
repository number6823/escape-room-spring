package com.escape.room.escaperoombackend.service.inventory;

import com.escape.room.escaperoombackend.domain.inventory.Inventory;
import com.escape.room.escaperoombackend.domain.item.Item;
import com.escape.room.escaperoombackend.domain.user.User;
import com.escape.room.escaperoombackend.dto.inventory.response.InventoryResponse;
import com.escape.room.escaperoombackend.repository.inventory.InventoryRepository;
import com.escape.room.escaperoombackend.repository.item.ItemRepository;
import com.escape.room.escaperoombackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> getMyInventory(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "존재하지 않는 사용자입니다."
                        )
                );

        return inventoryRepository
                .findAllByUser_IdOrderByObtainedAtDesc(user.getId())
                .stream()
                .map(InventoryResponse::new)
                .toList();
    }

    @Transactional
    public InventoryResponse acquireItem(
            Long itemId,
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "존재하지 않는 사용자입니다."
                        )
                );

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "존재하지 않는 아이템입니다."
                        )
                );

        if (inventoryRepository.existsByUserIdAndItemId(
                user.getId(),
                itemId
        )) {
            throw new IllegalArgumentException(
                    "이미 획득한 아이템입니다."
            );
        }

        Inventory inventory = new Inventory(
                user,
                item,
                LocalDateTime.now()
        );

        Inventory savedInventory =
                inventoryRepository.save(inventory);

        return new InventoryResponse(savedInventory);
    }
}