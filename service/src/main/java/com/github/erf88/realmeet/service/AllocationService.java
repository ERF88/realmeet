package com.github.erf88.realmeet.service;

import static com.github.erf88.realmeet.util.DateUtils.DEFAULT_TIMEZONE;
import static java.util.Objects.isNull;

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
import com.github.erf88.realmeet.util.PageUtils;
import com.github.erf88.realmeet.validator.AllocationValidator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

    public List<AllocationDTO> listAllocations(
        String employeeEmail,
        Long roomId,
        LocalDate startAt,
        LocalDate endAt,
        String orderBy,
        Integer limit
    ) {
        Pageable pageable = PageUtils.newPageable(null, limit, 0, orderBy, null);
        List<Allocation> allocations = allocationRepository.findAllWithFilters(
            employeeEmail,
            roomId,
            isNull(startAt) ? null : startAt.atTime(LocalTime.MIN).atOffset(DEFAULT_TIMEZONE),
            isNull(endAt) ? null : endAt.atTime(LocalTime.MAX).atOffset(DEFAULT_TIMEZONE),
            pageable
        );

        return allocations
            .stream()
            .map(allocationMapper::toAllocationDTO)
            .toList();
    }

    private boolean isAllocationInThePast(Allocation allocation) {
        return allocation.getEndAt().isBefore(DateUtils.now());
    }
}
