package com.github.erf88.realmeet.service;

import com.github.erf88.realmeet.api.model.AllocationDTO;
import com.github.erf88.realmeet.api.model.CreateAllocationDTO;
import com.github.erf88.realmeet.api.model.UpdateAllocationDTO;
import com.github.erf88.realmeet.domain.entity.Allocation;
import com.github.erf88.realmeet.domain.entity.Room;
import com.github.erf88.realmeet.domain.repository.AllocationRepository;
import com.github.erf88.realmeet.domain.repository.RoomRepository;
import com.github.erf88.realmeet.exception.AllocationCannotBeDeletedException;
import com.github.erf88.realmeet.exception.AllocationCannotBeUpdatedException;
import com.github.erf88.realmeet.exception.ResourceNotFoundException;
import com.github.erf88.realmeet.mapper.AllocationMapper;
import com.github.erf88.realmeet.util.DateUtils;
import com.github.erf88.realmeet.validator.AllocationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AllocationService {
    private final AllocationRepository allocationRepository;
    private final AllocationMapper allocationMapper;
    private final AllocationValidator allocationValidator;
    private final RoomRepository roomRepository;

    @Transactional
    public AllocationDTO createAllocation(CreateAllocationDTO createAllocationDTO) {
        Room room = roomRepository
            .findById(createAllocationDTO.getRoomId())
            .orElseThrow(() -> new ResourceNotFoundException("Room", createAllocationDTO.getRoomId()));

        allocationValidator.validate(createAllocationDTO);
        Allocation allocation = allocationMapper.toAllocation(createAllocationDTO, room);
        allocationRepository.save(allocation);
        return allocationMapper.toAllocationDTO(allocation);
    }

    @Transactional
    public void deleteAllocation(Long id) {
        Allocation allocation = getAllocationOrThrow(id);

        if (isAllocationInThePast(allocation)) {
            throw new AllocationCannotBeDeletedException();
        }

        allocationRepository.delete(allocation);
    }

    @Transactional
    public void updateAllocation(Long id, UpdateAllocationDTO updateAllocationDTO) {
        Allocation allocation = getAllocationOrThrow(id);

        if (isAllocationInThePast(allocation)) {
            throw new AllocationCannotBeUpdatedException();
        }
        allocationValidator.validate(id, updateAllocationDTO);
        allocationRepository.updateAllocation(
            id,
            updateAllocationDTO.getSubject(),
            updateAllocationDTO.getStartAt(),
            updateAllocationDTO.getEndAt()
        );
    }

    private Allocation getAllocationOrThrow(Long id) {
        return allocationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Allocation", id));
    }

    private boolean isAllocationInThePast(Allocation allocation) {
        return allocation.getEndAt().isBefore(DateUtils.now());
    }
}
