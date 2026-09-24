package com.escape.room.escaperoombackend.controller.item;

import com.escape.room.escaperoombackend.dto.inventory.response.InventoryResponse;
import com.escape.room.escaperoombackend.dto.item.response.ItemResponse;
import com.escape.room.escaperoombackend.service.inventory.InventoryService;
import com.escape.room.escaperoombackend.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private InventoryService inventoryService;

    @GetMapping("/room/{roomId}")
    public List<ItemResponse> getItems(
            @PathVariable Long roomId
    ) {
        return itemService.getItems(roomId);
    }

    @GetMapping("/{itemId}")
    public ItemResponse getItem(
            @PathVariable Long itemId
    ) {
        return itemService.getItem(itemId);
    }
    @PostMapping("/{itemId}/acquire")
    public InventoryResponse acquireItem(
            @PathVariable Long itemId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return inventoryService.acquireItem(
                itemId,
                email
        );
    }
}