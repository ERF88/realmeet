package com.github.erf88.realmeet.util;

import static java.util.Objects.isNull;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        int definedPage = isNull(page) ? 0 : page;
        int definedLimit = isNull(limit) ? maxLimit : Math.min(limit, maxLimit);
        Sort definedSort = parseOrderByFields(orderBy, validSortableFields);
        return PageRequest.of(definedPage, definedLimit, definedSort);
    }

    private static Sort parseOrderByFields(String orderBy, List<String> validSortableFields) {
        return null;
    }
}
