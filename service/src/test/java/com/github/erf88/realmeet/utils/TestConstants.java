package com.github.erf88.realmeet.utils;

import static com.github.erf88.realmeet.utils.DateUtils.now;

import java.time.OffsetDateTime;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class TestConstants {
    public static final long DEFAULT_ROOM_ID = 1L;
    public static final String DEFAULT_ROOM_NAME = "Room A";
    public static final int DEFAULT_ROOM_SEATS = 6;
    public static final String DEFAULT_ALLOCATION_SUBJECT = "Some Subject";
    public static final String DEFAULT_EMPLOYEE_NAME = "Eduardo";
    public static final String DEFAULT_EMPLOYEE_EMAIL = "erf88@email.com";
    public static final OffsetDateTime DEFAULT_ALLOCATION_START_AT = now().plusDays(1);
    public static final OffsetDateTime DEFAULT_ALLOCATION_END_AT = DEFAULT_ALLOCATION_START_AT.plusHours(1);
}
