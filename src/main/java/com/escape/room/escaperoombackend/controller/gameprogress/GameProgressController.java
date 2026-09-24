package com.escape.room.escaperoombackend.controller.gameprogress;


import com.escape.room.escaperoombackend.dto.gameprogress.response.GameProgressResponse;
import com.escape.room.escaperoombackend.service.gameprogress.GameProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games/{gameRecordId}/progress")
public class GameProgressController {

    private final GameProgressService gameProgressService;

    @GetMapping
    public List<GameProgressResponse> getProgress(
            @PathVariable Long gameRecordId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return gameProgressService.getProgress(
                gameRecordId,
                email
        );
    }
}
