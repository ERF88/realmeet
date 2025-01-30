package com.github.erf88.realmeet.unit;

import static com.github.erf88.realmeet.utils.MapperUtils.allocationMapper;
import static com.github.erf88.realmeet.utils.TestDataCreator.newCreateAllocationDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.erf88.realmeet.api.model.CreateAllocationDTO;
import com.github.erf88.realmeet.core.BaseUnitTest;
import com.github.erf88.realmeet.domain.entity.Allocation;
import com.github.erf88.realmeet.mapper.AllocationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AllocationMapperUnitTest extends BaseUnitTest {
    private AllocationMapper victim;

    @BeforeEach
    void setupEach() {
        victim = allocationMapper();
    }

    @Test
    void testToAllocation() {
        CreateAllocationDTO createAllocationDTO = newCreateAllocationDTO();
        Allocation allocation = victim.toAllocation(createAllocationDTO);

        assertEquals(createAllocationDTO.getRoomId(), allocation.getRoom().getId());
        assertEquals(createAllocationDTO.getSubject(), allocation.getSubject());
        assertEquals(createAllocationDTO.getEmployeeName(), allocation.getEmployee().getName());
        assertEquals(createAllocationDTO.getEmployeeEmail(), allocation.getEmployee().getEmail());
        assertEquals(createAllocationDTO.getStartAt(), allocation.getStartAt());
        assertEquals(createAllocationDTO.getEndAt(), allocation.getEndAt());
    }
}
