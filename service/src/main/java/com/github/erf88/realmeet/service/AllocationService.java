package com.github.erf88.realmeet.service;

import com.github.erf88.realmeet.api.model.AllocationDTO;
import com.github.erf88.realmeet.api.model.CreateAllocationDTO;
import com.github.erf88.realmeet.domain.entity.Allocation;
import com.github.erf88.realmeet.domain.entity.Room;
import com.github.erf88.realmeet.domain.repository.AllocationRepository;
import com.github.erf88.realmeet.domain.repository.RoomRepository;
import com.github.erf88.realmeet.exception.ResourceNotFoundException;
import com.github.erf88.realmeet.mapper.AllocationMapper;
import com.github.erf88.realmeet.validator.AllocationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AllocationService {
    private final AllocationRepository allocationRepository;
    private final AllocationMapper allocationMapper;
    private final AllocationValidator allocationValidator;
    private final RoomRepository roomRepository;

    public AllocationDTO createAllocation(CreateAllocationDTO createAllocationDTO) {
        Room room = roomRepository
            .findById(createAllocationDTO.getRoomId())
            .orElseThrow(() -> new ResourceNotFoundException("Room", createAllocationDTO.getRoomId()));

        allocationValidator.validate(createAllocationDTO);
        Allocation allocation = allocationMapper.toAllocation(createAllocationDTO, room);
        allocationRepository.save(allocation);
        return allocationMapper.toAllocationDTO(allocation);
    }
}
