package com.escape.room.escaperoombackend.controller.inventory;

import com.escape.room.escaperoombackend.dto.inventory.response.InventoryResponse;
import com.escape.room.escaperoombackend.service.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public List<InventoryResponse> getMyInventory(
            Authentication authentication
    ) {
        String email = authentication.getName();

        return inventoryService.getMyInventory(email);
    }
}
