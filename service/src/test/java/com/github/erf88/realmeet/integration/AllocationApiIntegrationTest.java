package com.github.erf88.realmeet.integration;

import static com.github.erf88.realmeet.utils.TestConstants.DEFAULT_ALLOCATION_ID;
import static com.github.erf88.realmeet.utils.TestDataCreator.*;
import static org.junit.jupiter.api.Assertions.*;

import com.github.erf88.realmeet.api.facade.AllocationApi;
import com.github.erf88.realmeet.api.model.AllocationDTO;
import com.github.erf88.realmeet.api.model.CreateAllocationDTO;
import com.github.erf88.realmeet.core.BaseIntegrationTest;
import com.github.erf88.realmeet.domain.entity.Allocation;
import com.github.erf88.realmeet.domain.entity.Room;
import com.github.erf88.realmeet.domain.repository.AllocationRepository;
import com.github.erf88.realmeet.domain.repository.RoomRepository;
import com.github.erf88.realmeet.util.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

class AllocationApiIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private AllocationApi api;

    @Autowired
    private AllocationRepository allocationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    @Override
    protected void setupEach() throws Exception {
        setLocalHostBasePath(api.getApiClient(), "/v1");
    }

    @Test
    void testCreateAllocationSuccess() {
        Room room = roomRepository.saveAndFlush(newRoomBuilder().build());
        CreateAllocationDTO createAllocationDTO = newCreateAllocationDTO().roomId(room.getId());
        AllocationDTO allocationDTO = api.createAllocation(createAllocationDTO);

        assertNotNull(allocationDTO.getId());
        assertEquals(room.getId(), allocationDTO.getRoomId());
        assertEquals(createAllocationDTO.getSubject(), allocationDTO.getSubject());
        assertEquals(createAllocationDTO.getEmployeeName(), allocationDTO.getEmployeeName());
        assertEquals(createAllocationDTO.getEmployeeEmail(), allocationDTO.getEmployeeEmail());
        assertTrue(createAllocationDTO.getStartAt().isEqual(allocationDTO.getStartAt()));
        assertTrue(createAllocationDTO.getEndAt().isEqual(allocationDTO.getEndAt()));
    }

    @Test
    void testCreateAllocationValidationError() {
        Room room = roomRepository.saveAndFlush(newRoomBuilder().build());
        assertThrows(
            HttpClientErrorException.UnprocessableEntity.class,
            () -> api.createAllocation(newCreateAllocationDTO().roomId(room.getId()).subject(null))
        );
    }

    @Test
    void testCreateAllocationWhenRoomDoesNotExist() {
        assertThrows(HttpClientErrorException.NotFound.class, () -> api.createAllocation(newCreateAllocationDTO()));
    }

    @Test
    void testDeleteAllocationSuccess() {
        Room room = roomRepository.saveAndFlush(newRoomBuilder().build());
        Allocation allocation = allocationRepository.saveAndFlush(newAllocationBuilder().room(room).build());

        api.deleteAllocation(allocation.getId());
        assertFalse(allocationRepository.findById(allocation.getId()).isPresent());
    }

    @Test
    void testDeleteAllocationInTheePast() {
        Room room = roomRepository.saveAndFlush(newRoomBuilder().build());
        Allocation allocation = allocationRepository.saveAndFlush(
            newAllocationBuilder()
                .room(room)
                .startAt(DateUtils.now().minusDays(1))
                .endAt(DateUtils.now().minusDays(1).plusHours(1))
                .build()
        );

        assertThrows(
            HttpClientErrorException.UnprocessableEntity.class,
            () -> api.deleteAllocation(allocation.getId())
        );
    }

    @Test
    void testDeleteAllocationDoesNotExist() {
        assertThrows(HttpClientErrorException.NotFound.class, () -> api.deleteAllocation(DEFAULT_ALLOCATION_ID));
    }
}
