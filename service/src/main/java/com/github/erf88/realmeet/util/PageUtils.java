package com.github.erf88.realmeet.util;

import static java.util.Objects.isNull;

import com.github.erf88.realmeet.exception.InvalidOrderByFieldException;
import java.util.List;
import java.util.stream.Stream;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
        if (isNull(validSortableFields) || validSortableFields.isEmpty()) {
            throw new IllegalArgumentException("No valid sortable fields were defined");
        }

        if (StringUtils.isBlank(orderBy)) {
            return Sort.unsorted();
        }

        return Sort.by(Stream.of(orderBy.split(",")).map(f -> mapToOrder(validSortableFields, f)).toList());
    }

    private static Sort.Order mapToOrder(List<String> validSortableFields, String field) {
        String fieldsName;
        Sort.Order order;

        if (field.startsWith("-")) {
            fieldsName = field.substring(1);
            order = Sort.Order.desc(fieldsName);
        } else {
            fieldsName = field;
            order = Sort.Order.asc(fieldsName);
        }

        if (!validSortableFields.contains(fieldsName)) {
            throw new InvalidOrderByFieldException();
        }

        return order;
    }
}
