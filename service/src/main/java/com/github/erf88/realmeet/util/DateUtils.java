package com.github.erf88.realmeet.util;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtils {
    public static final ZoneOffset DEFAULT_TIMEZONE = ZoneOffset.of("+03:00");

    public static OffsetDateTime now() {
        return OffsetDateTime.now(DEFAULT_TIMEZONE).truncatedTo(ChronoUnit.MILLIS);
    }

    public static boolean isOverlapping(
        OffsetDateTime start1,
        OffsetDateTime end1,
        OffsetDateTime start2,
        OffsetDateTime end2
    ) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }
}
