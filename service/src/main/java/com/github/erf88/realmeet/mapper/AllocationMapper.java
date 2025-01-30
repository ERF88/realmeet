package com.github.erf88.realmeet.mapper;

import com.github.erf88.realmeet.api.model.AllocationDTO;
import com.github.erf88.realmeet.api.model.CreateAllocationDTO;
import com.github.erf88.realmeet.api.model.CreateRoomDTO;
import com.github.erf88.realmeet.api.model.RoomDTO;
import com.github.erf88.realmeet.domain.entity.Allocation;
import com.github.erf88.realmeet.domain.entity.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AllocationMapper {
    AllocationDTO toAllocationDTO(Allocation allocation);

    Allocation toAllocation(CreateAllocationDTO createAllocationDTO);
}
