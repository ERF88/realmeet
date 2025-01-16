package com.github.erf88.realmeet.service;

import com.github.erf88.realmeet.api.model.RoomDTO;
import com.github.erf88.realmeet.domain.repository.RoomRepository;
import com.github.erf88.realmeet.exception.RoomNotFoundException;
import java.util.Objects;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room getRoomById(Long id) {
        Objects.requireNonNull(id);
        return roomRepository.findById(id)
                .map(room -> new RoomDTO().id(room.getId()).name(room.getName()).seats(room.getSeats()))
                .orElseThrow(RoomNotFoundException::new);
    }

}
