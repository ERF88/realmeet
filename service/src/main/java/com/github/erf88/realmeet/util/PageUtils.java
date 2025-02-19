package com.github.erf88.realmeet.util;

import java.util.List;
import org.springframework.data.domain.Pageable;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class PageUtils {

    public static Pageable newPageable(
        Integer page,
        Integer limit,
        int maxLimit,
        String orderBy,
        List<String> validSortableFields
    ) {

        return null;
    }
}
