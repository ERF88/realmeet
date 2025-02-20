package com.github.erf88.realmeet.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class Constants {
    public static final String ALLOCATIONS_MAX_FILTER_LIMIT = "${realmeet.allocations.maxFilterLimit:50}";
}
