package com.escape.room.escaperoombackend.service.item;

import com.escape.room.escaperoombackend.domain.item.Item;
import com.escape.room.escaperoombackend.dto.item.response.ItemResponse;
import com.escape.room.escaperoombackend.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<ItemResponse> getItems(Long roomId) {

        return itemRepository
                .findAllByRoom_IdOrderByIdAsc(roomId)
                .stream()
                .map(ItemResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ItemResponse getItem(Long itemId) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "존재하지 않는 아이템입니다."
                        )
                );

        return new ItemResponse(item);
    }
}