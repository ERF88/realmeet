package com.github.erf88.realmeet.unit;

import static com.github.erf88.realmeet.utils.MapperUtils.roomMapper;
import static com.github.erf88.realmeet.utils.TestConstants.DEFAULT_ROOM_ID;
import static com.github.erf88.realmeet.utils.TestDataCreator.newRoomBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.erf88.realmeet.api.model.RoomDTO;
import com.github.erf88.realmeet.core.BaseUnitTest;
import com.github.erf88.realmeet.domain.entity.Room;
import com.github.erf88.realmeet.mapper.RoomMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomMapperUnitTest extends BaseUnitTest {
    private RoomMapper victim;

    @BeforeEach
    void setupEach() {
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
