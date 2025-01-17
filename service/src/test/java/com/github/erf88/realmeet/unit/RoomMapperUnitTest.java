package com.github.erf88.realmeet.unit;

import static com.github.erf88.realmeet.utils.MapperUtils.roomMapper;
import static com.github.erf88.realmeet.utils.TestConstants.*;
import static com.github.erf88.realmeet.utils.TestDataCreator.newRoomBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.erf88.realmeet.api.model.RoomDTO;
import com.github.erf88.realmeet.domain.entity.Room;
import com.github.erf88.realmeet.mapper.RoomMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoomMapperUnitTest {

    private RoomMapper victim;

    @BeforeEach
    void setEach() {
        victim = roomMapper();
    }

    @Test
    void testToRoomDTO() {
        Room room = newRoomBuilder().id(DEFAULT_ROOM_ID).build();
        RoomDTO roomDTO = victim.toRoomDTO(room);

        assertEquals(room.getId(), roomDTO.getId());
        assertEquals(room.getName(), roomDTO.getName());
        assertEquals(room.getSeats(), roomDTO.getSeats());
    }

}
