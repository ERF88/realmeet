package com.github.erf88.realmeet.integration;

import static com.github.erf88.realmeet.utils.TestConstants.*;
import static com.github.erf88.realmeet.utils.TestDataCreator.*;
import static org.junit.jupiter.api.Assertions.*;

import com.github.erf88.realmeet.api.facade.AllocationApi;
import com.github.erf88.realmeet.api.model.AllocationDTO;
import com.github.erf88.realmeet.api.model.CreateAllocationDTO;
import com.github.erf88.realmeet.api.model.UpdateAllocationDTO;
import com.github.erf88.realmeet.core.BaseIntegrationTest;
import com.github.erf88.realmeet.domain.entity.Allocation;
import com.github.erf88.realmeet.domain.entity.Room;
import com.github.erf88.realmeet.domain.repository.AllocationRepository;
import com.github.erf88.realmeet.domain.repository.RoomRepository;
import com.github.erf88.realmeet.util.DateUtils;
import com.github.erf88.realmeet.utils.TestDataCreator;
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
    void filterAllAllocations() {
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
            null
        );

        assertEquals(3, allocationDTOS.size());
        assertEquals(allocation1.getSubject(), allocationDTOS.get(0).getSubject());
        assertEquals(allocation2.getSubject(), allocationDTOS.get(1).getSubject());
        assertEquals(allocation3.getSubject(), allocationDTOS.get(2).getSubject());
    }
}
