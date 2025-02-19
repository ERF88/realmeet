package com.github.erf88.realmeet.integration;

import static com.github.erf88.realmeet.util.DateUtils.now;
import static com.github.erf88.realmeet.utils.TestConstants.*;
import static com.github.erf88.realmeet.utils.TestDataCreator.*;
import static org.junit.jupiter.api.Assertions.*;

import com.github.erf88.realmeet.api.facade.AllocationApi;
import com.github.erf88.realmeet.api.model.AllocationDTO;
import com.github.erf88.realmeet.api.model.CreateAllocationDTO;
import com.github.erf88.realmeet.api.model.UpdateAllocationDTO;
import com.github.erf88.realmeet.core.BaseIntegrationTest;
import com.github.erf88.realmeet.domain.entity.Allocation;
import com.github.erf88.realmeet.domain.entity.Employee;
import com.github.erf88.realmeet.domain.entity.Room;
import com.github.erf88.realmeet.domain.repository.AllocationRepository;
import com.github.erf88.realmeet.domain.repository.RoomRepository;
import com.github.erf88.realmeet.util.DateUtils;
import com.github.erf88.realmeet.utils.TestDataCreator;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

class AllocationApiFilterIntegrationTest extends BaseIntegrationTest {
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
    void testFilterAllAllocations() {
        Room room = roomRepository.saveAndFlush(newRoomBuilder().build());

        Allocation allocation1 = allocationRepository.saveAndFlush(
            newAllocationBuilder().room(room).subject(DEFAULT_ALLOCATION_SUBJECT + 1).build()
        );

        Allocation allocation2 = allocationRepository.saveAndFlush(
            newAllocationBuilder().room(room).subject(DEFAULT_ALLOCATION_SUBJECT + 2).build()
        );

        Allocation allocation3 = allocationRepository.saveAndFlush(
            newAllocationBuilder().room(room).subject(DEFAULT_ALLOCATION_SUBJECT + 3).build()
        );

        List<AllocationDTO> allocationDTOS = api.listAllocations(
            null,
            null,
            null,
            null,
            null,
            null
        );

        assertEquals(3, allocationDTOS.size());
        assertEquals(allocation1.getSubject(), allocationDTOS.get(0).getSubject());
        assertEquals(allocation2.getSubject(), allocationDTOS.get(1).getSubject());
        assertEquals(allocation3.getSubject(), allocationDTOS.get(2).getSubject());
    }

    @Test
    void testFilterAllocationsByRoomId() {
        Room roomA = roomRepository.saveAndFlush(newRoomBuilder().name(DEFAULT_ROOM_NAME + "A").build());
        Room roomB = roomRepository.saveAndFlush(newRoomBuilder().name(DEFAULT_ROOM_NAME + "A").build());

        Allocation allocation1 = allocationRepository.saveAndFlush(newAllocationBuilder().room(roomA).build());
        Allocation allocation2 = allocationRepository.saveAndFlush(newAllocationBuilder().room(roomA).build());
        allocationRepository.saveAndFlush(newAllocationBuilder().room(roomB).build());

        List<AllocationDTO> allocationDTOS = api.listAllocations(
            null,
            roomA.getId(),
            null,
            null,
            null,
            null
        );

        assertEquals(2, allocationDTOS.size());
        assertEquals(allocation1.getId(), allocationDTOS.get(0).getId());
        assertEquals(allocation2.getId(), allocationDTOS.get(1).getId());
    }

    @Test
    void testFilterAllocationsByEmployeeEmail() {
        Room room = roomRepository.saveAndFlush(newRoomBuilder().build());
        Employee employee1 = newEmployeeBuilder().email(DEFAULT_EMPLOYEE_EMAIL + 1).build();
        Employee employee2 = newEmployeeBuilder().email(DEFAULT_EMPLOYEE_EMAIL + 2).build();

        Allocation allocation1 = allocationRepository.saveAndFlush(
            newAllocationBuilder().room(room).employee(employee1).build()
        );

        Allocation allocation2 = allocationRepository.saveAndFlush(
            newAllocationBuilder().room(room).employee(employee1).build()
        );

        Allocation allocation3 = allocationRepository.saveAndFlush(
            newAllocationBuilder().room(room).employee(employee2).build()
        );

        List<AllocationDTO> allocationDTOS = api.listAllocations(
            employee1.getEmail(),
            null,
            null,
            null,
            null,
            null
        );

        assertEquals(2, allocationDTOS.size());
        assertEquals(allocation1.getId(), allocationDTOS.get(0).getId());
        assertEquals(allocation2.getId(), allocationDTOS.get(1).getId());
    }

    @Test
    void testFilterAllocationsByDateRange() {
        OffsetDateTime baseStartAt = now().plusDays(2).withHour(14).withMinute(0);
        OffsetDateTime baseEndAt = now().plusDays(4).withHour(20).withMinute(0);

        Room room = roomRepository.saveAndFlush(newRoomBuilder().build());

        Allocation allocation1 = allocationRepository.saveAndFlush(
            newAllocationBuilder().room(room).startAt(baseStartAt.plusHours(1)).endAt(baseStartAt.plusHours(2)).build()
        );

        Allocation allocation2 = allocationRepository.saveAndFlush(
            newAllocationBuilder().room(room).startAt(baseStartAt.plusHours(4)).endAt(baseStartAt.plusHours(5)).build()
        );

        Allocation allocation3 = allocationRepository.saveAndFlush(
            newAllocationBuilder()
                .room(room).startAt(baseEndAt.plusDays(1)).endAt(baseEndAt.plusDays(3).plusHours(1)).build()
        );
        allocationRepository.saveAndFlush(newAllocationBuilder().room(room).build());

        List<AllocationDTO> allocationDTOS = api.listAllocations(
            null,
            null,
             baseStartAt.toLocalDate(),
             baseEndAt.toLocalDate(),
            null,
            null
        );

        assertEquals(2, allocationDTOS.size());
        assertEquals(allocation1.getId(), allocationDTOS.get(0).getId());
        assertEquals(allocation2.getId(), allocationDTOS.get(1).getId());
    }
}
