package com.escape.room.escaperoombackend.controller.gamerecord;

import com.escape.room.escaperoombackend.dto.gamerecord.response.GameRecordResponse;
import com.escape.room.escaperoombackend.service.gamerecord.GameRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameRecordController {

    private final GameRecordService gameRecordService;

    @PostMapping
    public GameRecordResponse startGame(Authentication authentication) {

        String email = authentication.getName();

        return gameRecordService.startGame(email);
    }
}
