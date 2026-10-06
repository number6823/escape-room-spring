package com.escape.room.escaperoombackend.service.clue;

import com.escape.room.escaperoombackend.domain.clue.Clue;
import com.escape.room.escaperoombackend.dto.clue.response.ClueResponse;
import com.escape.room.escaperoombackend.exception.NotFoundException;
import com.escape.room.escaperoombackend.repository.clue.ClueRepository;
import com.escape.room.escaperoombackend.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClueService {

    private final ClueRepository clueRepository;
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public List<ClueResponse> getClues(Long roomId) {

        roomRepository.findById(roomId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "존재하지 않는 방입니다."
                        )
                );

        return clueRepository
                .findAllByRoom_IdOrderByIdAsc(roomId)
                .stream()
                .map(ClueResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClueResponse getClue(Long clueId) {

        Clue clue = clueRepository.findById(clueId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "존재하지 않는 단서입니다."
                        )
                );

        return new ClueResponse(clue);
    }
}