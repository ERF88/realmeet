package com.github.erf88.realmeet.service;

import static java.util.Objects.requireNonNull;

import com.github.erf88.realmeet.api.model.CreateRoomDTO;
import com.github.erf88.realmeet.api.model.RoomDTO;
import com.github.erf88.realmeet.api.model.UpdateRoomDTO;
import com.github.erf88.realmeet.domain.entity.Room;
import com.github.erf88.realmeet.domain.repository.RoomRepository;
import com.github.erf88.realmeet.exception.ResourceNotFoundException;
import com.github.erf88.realmeet.mapper.RoomMapper;
import com.github.erf88.realmeet.validator.RoomValidator;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final RoomValidator roomValidator;

    public RoomDTO getRoomById(Long id) {
        Room room = getActiveRoomOrThrow(id);
        return roomMapper.toRoomDTO(room);
    }

    @Transactional
    public RoomDTO createRoom(CreateRoomDTO createRoomDTO) {
        roomValidator.validate(createRoomDTO);
        Room room = roomMapper.toRoom(createRoomDTO);
        roomRepository.save(room);
        return roomMapper.toRoomDTO(room);
    }

    @Transactional
    public void deleteRoom(Long id) {
        getActiveRoomOrThrow(id);
        roomRepository.deactivate(id);
    }

    @Transactional
    public void updateRoom(Long id, UpdateRoomDTO updateRoomDTO) {
        getActiveRoomOrThrow(id);
        roomValidator.validate(id, updateRoomDTO);
        roomRepository.updateRoom(id, updateRoomDTO.getName(), updateRoomDTO.getSeats());
    }

    private Room getActiveRoomOrThrow(Long id) {
        requireNonNull(id);
        return roomRepository
            .findByIdAndActive(id, Boolean.TRUE)
            .orElseThrow(() -> new ResourceNotFoundException("Room", id));
    }
}
