package com.github.erf88.realmeet.integration;

import static com.github.erf88.realmeet.utils.TestConstants.DEFAULT_ROOM_ID;
import static com.github.erf88.realmeet.utils.TestDataCreator.newCreateRoomDTO;
import static com.github.erf88.realmeet.utils.TestDataCreator.newRoomBuilder;
import static org.junit.jupiter.api.Assertions.*;

import com.github.erf88.realmeet.api.facade.RoomApi;
import com.github.erf88.realmeet.api.model.CreateRoomDTO;
import com.github.erf88.realmeet.api.model.RoomDTO;
import com.github.erf88.realmeet.api.model.UpdateRoomDTO;
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

    @Test
    void testCreateRoomSuccess() {
        CreateRoomDTO createRoomDTO = newCreateRoomDTO();
        RoomDTO roomDTO = api.createRoom(createRoomDTO);

        assertNotNull(roomDTO.getId());
        assertEquals(createRoomDTO.getName(), roomDTO.getName());
        assertEquals(createRoomDTO.getSeats(), roomDTO.getSeats());

        Room room = roomRepository.findById(roomDTO.getId()).orElseThrow();
        assertEquals(roomDTO.getName(), room.getName());
        assertEquals(roomDTO.getSeats(), room.getSeats());
    }

    @Test
    void testCreateRoomValidationError() {
        assertThrows(
            HttpClientErrorException.UnprocessableEntity.class,
            () -> api.createRoom(newCreateRoomDTO().name(null))
        );
    }

    @Test
    void testDeleteRoomSuccess() {
        Long id = roomRepository.saveAndFlush(newRoomBuilder().build()).getId();
        api.deleteRoom(id);

        assertFalse(roomRepository.findById(id).orElseThrow().getActive());
    }

    @Test
    void testDeleteRoomDoesNotExist() {
        assertThrows(HttpClientErrorException.class, () -> api.deleteRoom(DEFAULT_ROOM_ID));
    }

    @Test
    void testUpdateRoomSuccess() {
        Room room = roomRepository.saveAndFlush(newRoomBuilder().build());
        UpdateRoomDTO updateRoomDTO = new UpdateRoomDTO().name(room.getName() + "_").seats(room.getSeats() + 1);

        api.updateRoom(room.getId(), updateRoomDTO);

        Room updatedRoom = roomRepository.findById(room.getId()).orElseThrow();

        assertEquals(updateRoomDTO.getName(), updatedRoom.getName());
        assertEquals(updateRoomDTO.getSeats(), updatedRoom.getSeats());
    }

    @Test
    void testUpdateRoomDoesNotExists() {
        UpdateRoomDTO updateRoomDTO = new UpdateRoomDTO().name("Room").seats(10);

        assertThrows(HttpClientErrorException.class, () -> api.updateRoom(DEFAULT_ROOM_ID, updateRoomDTO));
    }

    @Test
    void testUpdateRoomValidationError() {
        UpdateRoomDTO updateRoomDTO = new UpdateRoomDTO().name(null).seats(10);

        assertThrows(
            HttpClientErrorException.UnprocessableEntity.class,
            () -> api.updateRoom(DEFAULT_ROOM_ID, updateRoomDTO)
        );
    }
}
