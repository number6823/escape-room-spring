package com.escape.room.escaperoombackend.controller.clue;

import com.escape.room.escaperoombackend.dto.clue.response.ClueResponse;
import com.escape.room.escaperoombackend.service.clue.ClueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clues")
public class ClueController {

    private final ClueService clueService;

    @GetMapping("/room/{roomId}")
    public List<ClueResponse> getClues(
            @PathVariable Long roomId
    ) {
        return clueService.getClues(roomId);
    }

    @GetMapping("/{clueId}")
    public ClueResponse getClue(
            @PathVariable Long clueId
    ) {
        return clueService.getClue(clueId);
    }
}