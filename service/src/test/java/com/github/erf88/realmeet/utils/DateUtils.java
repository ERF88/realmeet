package com.github.erf88.realmeet.utils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtils {
    private static final ZoneOffset DEFAULT_TIMEZONE = ZoneOffset.of("+03:00");

    public static OffsetDateTime now() {
        return OffsetDateTime.now(DEFAULT_TIMEZONE);
    }
}
