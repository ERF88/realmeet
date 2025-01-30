package com.github.erf88.realmeet.mapper;

import com.github.erf88.realmeet.api.model.AllocationDTO;
import com.github.erf88.realmeet.api.model.CreateAllocationDTO;
import com.github.erf88.realmeet.api.model.CreateRoomDTO;
import com.github.erf88.realmeet.api.model.RoomDTO;
import com.github.erf88.realmeet.domain.entity.Allocation;
import com.github.erf88.realmeet.domain.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AllocationMapper {
    @Mapping(source = "room", target = "room")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "createAllocationDTO.employeeName", target = "employee.name")
    @Mapping(source = "createAllocationDTO.employeeEmail", target = "employee.email")
    Allocation toAllocation(CreateAllocationDTO createAllocationDTO, Room room);

    @Mapping(source = "employee.name", target = "employeeName")
    @Mapping(source = "employee.email", target = "employeeEmail")
    AllocationDTO toAllocationDTO(Allocation allocation);
}
