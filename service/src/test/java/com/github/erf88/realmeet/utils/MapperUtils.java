package com.github.erf88.realmeet.utils;

import com.github.erf88.realmeet.mapper.RoomMapper;
import org.mapstruct.factory.Mappers;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class MapperUtils {

    public static RoomMapper roomMapper() {
        return Mappers.getMapper(RoomMapper.class);
    }

}
