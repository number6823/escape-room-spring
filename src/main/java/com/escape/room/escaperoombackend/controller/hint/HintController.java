package com.escape.room.escaperoombackend.controller.hint;

import com.escape.room.escaperoombackend.dto.hint.response.HintResponse;
import com.escape.room.escaperoombackend.dto.hint.response.UseHintResponse;
import com.escape.room.escaperoombackend.dto.hint.response.UsedHintResponse;
import com.escape.room.escaperoombackend.service.hint.HintService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hints")
public class HintController {

    private final HintService hintService;

    @GetMapping("/puzzle/{puzzleId}")
    public List<HintResponse> getHints(@PathVariable Long puzzleId) {
        return hintService.getHints(puzzleId);
    }

    @GetMapping("/game/{gameRecordId}")
    public List<UsedHintResponse> getUsedHints(
            @PathVariable Long gameRecordId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return hintService.getUsedHints(
                gameRecordId,
                email
        );
    }

    @GetMapping("/{hintId}")
    public HintResponse getHint(@PathVariable Long hintId) {
        return hintService.getHint(hintId);
    }

    @PostMapping("/{hintId}/use")
    public UseHintResponse useHint(
            @PathVariable Long hintId,
            @RequestParam Long gameRecordId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return hintService.useHint(
                hintId,
                gameRecordId,
                email
        );
    }
}