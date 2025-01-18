package com.github.erf88.realmeet.unit;

import static com.github.erf88.realmeet.utils.MapperUtils.roomMapper;
import static com.github.erf88.realmeet.utils.TestConstants.DEFAULT_ROOM_ID;
import static com.github.erf88.realmeet.utils.TestDataCreator.newRoomBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.github.erf88.realmeet.api.model.RoomDTO;
import com.github.erf88.realmeet.core.BaseUnitTest;
import com.github.erf88.realmeet.domain.entity.Room;
import com.github.erf88.realmeet.domain.repository.RoomRepository;
import com.github.erf88.realmeet.exception.ResourceNotFoundException;
import com.github.erf88.realmeet.service.RoomService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class RoomServiceUnitTest extends BaseUnitTest {
    private RoomService victim;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    void setEach() {
        victim = new RoomService(roomRepository, roomMapper());
    }

    @Test
    void testGetRoomByIdSuccess() {
        Room room = newRoomBuilder().id(DEFAULT_ROOM_ID).build();
        when(roomRepository.findByIdAndActive(DEFAULT_ROOM_ID, Boolean.TRUE)).thenReturn(Optional.of(room));

        RoomDTO roomDTO = victim.getRoomById(DEFAULT_ROOM_ID);

        assertEquals(room.getId(), roomDTO.getId());
        assertEquals(room.getName(), roomDTO.getName());
        assertEquals(room.getSeats(), roomDTO.getSeats());
    }

    @Test
    void testGetRoomByIdNotFound() {
        Room room = newRoomBuilder().id(DEFAULT_ROOM_ID).build();
        when(roomRepository.findByIdAndActive(DEFAULT_ROOM_ID, Boolean.TRUE)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> victim.getRoomById(DEFAULT_ROOM_ID));
    }
}
