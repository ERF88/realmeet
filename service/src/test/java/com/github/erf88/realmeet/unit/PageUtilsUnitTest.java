package com.github.erf88.realmeet.unit;

import static com.github.erf88.realmeet.domain.entity.Allocation.SORTABLE_FIELDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.erf88.realmeet.core.BaseUnitTest;
import com.github.erf88.realmeet.exception.InvalidOrderByFieldException;
import com.github.erf88.realmeet.util.PageUtils;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtilsUnitTest extends BaseUnitTest {

    @Test
    void testNewPageableWhenPageIsNullAndLimitIsNullAndOrderByIsNull() {
        Pageable pageable = PageUtils
            .newPageable(null, null, 10, null, SORTABLE_FIELDS);

        assertEquals(0, pageable.getPageNumber());
        assertEquals(10, pageable.getPageSize());
        assertEquals(Sort.unsorted(), pageable.getSort());
    }

    @Test
    void testNewPageableWhenPageIsNegative() {
        assertThrows(
            IllegalArgumentException.class,
            () -> PageUtils.newPageable(-1, null, 10, null, SORTABLE_FIELDS)
        );
    }

    @Test
    void testNewPageableWhenLimitIsInvalid() {
        assertThrows(
            IllegalArgumentException.class,
            () -> PageUtils.newPageable(null, 0, 10, null, SORTABLE_FIELDS)
        );
    }

    @Test
    void testNewPageableWhenLimitExceedsMaximum() {
        Pageable pageable = PageUtils
            .newPageable(null, 30, 10, null, SORTABLE_FIELDS);

        assertEquals(10, pageable.getPageSize());
    }

    @Test
    void testNewPageableWhenOrderByIsNull() {
        assertThrows(
            IllegalArgumentException.class,
            () -> PageUtils.newPageable(null, 0, 10, null, null)
        );
    }

    @Test
    void testNewPageableWhenOrderByIsEmpty() {
        assertThrows(
            IllegalArgumentException.class,
            () -> PageUtils.newPageable(null, 0, 10, null, Collections.emptyList())
        );
    }

    @Test
    void testNewPageableWhenOrderByAscIsValid() {
        Pageable pageable = PageUtils
            .newPageable(null, null, 10, SORTABLE_FIELDS.getFirst(), SORTABLE_FIELDS);

        assertEquals(Sort.by(Sort.Order.asc(SORTABLE_FIELDS.getFirst())), pageable.getSort());
    }

    @Test
    void testNewPageableWhenOrderByDescIsValid() {
        Pageable pageable = PageUtils
            .newPageable(null, null, 10, "-".concat(SORTABLE_FIELDS.getFirst()), SORTABLE_FIELDS);

        assertEquals(Sort.by(Sort.Order.desc(SORTABLE_FIELDS.getFirst())), pageable.getSort());
    }

    @Test
    void testNewPageableWhenOrderByFieldIsInvalid() {
        assertThrows(
            InvalidOrderByFieldException.class,
            () -> PageUtils.newPageable(null, null, 10, "invalid", SORTABLE_FIELDS)
        );
    }
}
