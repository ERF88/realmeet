package com.github.erf88.realmeet.utils;

import static com.github.erf88.realmeet.utils.TestConstants.DEFAULT_ROOM_NAME;
import static com.github.erf88.realmeet.utils.TestConstants.DEFAULT_ROOM_SEATS;

import com.github.erf88.realmeet.domain.entity.Room;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class TestDataCreator {

    public static Room.Builder newRoomBuilder() {
        return Room.newBuilder()
                .name(DEFAULT_ROOM_NAME)
                .seats(DEFAULT_ROOM_SEATS);
    }

}
