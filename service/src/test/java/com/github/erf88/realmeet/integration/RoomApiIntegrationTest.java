package com.github.erf88.realmeet.integration;

import static com.github.erf88.realmeet.utils.TestConstants.DEFAULT_ROOM_ID;
import static com.github.erf88.realmeet.utils.TestDataCreator.newRoomBuilder;
import static org.junit.jupiter.api.Assertions.*;

import com.github.erf88.realmeet.api.facade.RoomApi;
import com.github.erf88.realmeet.api.model.RoomDTO;
import com.github.erf88.realmeet.core.BaseIntegrationTest;
import com.github.erf88.realmeet.domain.entity.Room;
import com.github.erf88.realmeet.domain.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

class RoomApiIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private RoomApi api;

    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    @Override
    protected void setupEach() throws Exception {
        setLocalHostBasePath(api.getApiClient(), "/v1");
    }

    @Test
    void testGetRoomSuccess() {
        Room room = newRoomBuilder().build();
        roomRepository.saveAndFlush(room);

        assertNotNull(room.getId());
        assertTrue(room.getActive());

        RoomDTO roomDTO = api.getRoom(room.getId());

        assertEquals(room.getId(), roomDTO.getId());
        assertEquals(room.getName(), roomDTO.getName());
        assertEquals(room.getSeats(), roomDTO.getSeats());
    }

    @Test
    void testGetRoomInactive() {
        Room room = newRoomBuilder().active(false).build();
        roomRepository.saveAndFlush(room);

        assertFalse(room.getActive());

        assertThrows(HttpClientErrorException.class, () -> api.getRoom(room.getId()));
    }

    @Test
    void testGetRoomDoesNotExist() {
        assertThrows(HttpClientErrorException.class, () -> api.getRoom(DEFAULT_ROOM_ID));
    }
}
