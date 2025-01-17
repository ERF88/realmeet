package com.github.erf88.realmeet.service;

import com.github.erf88.realmeet.api.model.RoomDTO;
import com.github.erf88.realmeet.domain.repository.RoomRepository;
import com.github.erf88.realmeet.exception.ResourceNotFoundException;
import com.github.erf88.realmeet.mapper.RoomMapper;
import java.util.Objects;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomDTO getRoomById(Long id) {
        Objects.requireNonNull(id);
        return roomRepository.findById(id)
                .map(roomMapper::toRoomDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Room", id));
    }

}
