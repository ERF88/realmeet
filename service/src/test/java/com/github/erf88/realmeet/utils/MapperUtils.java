package com.github.erf88.realmeet.utils;

import com.github.erf88.realmeet.mapper.AllocationMapper;
import com.github.erf88.realmeet.mapper.RoomMapper;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class MapperUtils {

    public static RoomMapper roomMapper() {
        return Mappers.getMapper(RoomMapper.class);
    }

    public static AllocationMapper allocationMapper() {
        return Mappers.getMapper(AllocationMapper.class);
    }
}
