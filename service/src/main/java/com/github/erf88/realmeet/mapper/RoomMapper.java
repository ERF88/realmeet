package com.github.erf88.realmeet.mapper;

import com.github.erf88.realmeet.api.model.RoomDTO;
import com.github.erf88.realmeet.domain.entity.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class RoomMapper {

    public abstract RoomDTO toRoomDTO(Room room);

}
