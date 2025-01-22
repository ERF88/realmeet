package com.github.erf88.realmeet.service;

import com.github.erf88.realmeet.api.model.CreateRoomDTO;
import com.github.erf88.realmeet.api.model.RoomDTO;
import com.github.erf88.realmeet.domain.entity.Room;
import com.github.erf88.realmeet.domain.repository.RoomRepository;
import com.github.erf88.realmeet.exception.ResourceNotFoundException;
import com.github.erf88.realmeet.mapper.RoomMapper;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomDTO getRoomById(Long id) {
        Objects.requireNonNull(id);
        return roomRepository
            .findByIdAndActive(id, Boolean.TRUE)
            .map(roomMapper::toRoomDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Room", id));
    }

    public RoomDTO createRoom(CreateRoomDTO createRoomDTO) {
        Room room = roomMapper.toRoom(createRoomDTO);
        roomRepository.save(room);
        return roomMapper.toRoomDTO(room);
    }
}
