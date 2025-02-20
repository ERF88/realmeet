package com.github.erf88.realmeet.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.erf88.realmeet.core.BaseUnitTest;
import com.github.erf88.realmeet.domain.entity.Allocation;
import com.github.erf88.realmeet.util.PageUtils;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtilsUnitTest extends BaseUnitTest {

    @Test
    void testNewPageableWhenPageIsNullAndLimitIsNullAndOrderByIsNull() {
        Pageable pageable = PageUtils
            .newPageable(null, null, 10, null, Allocation.SORTABLE_FIELDS);

        assertEquals(0, pageable.getPageNumber());
        assertEquals(10, pageable.getPageSize());
        assertEquals(Sort.unsorted(), pageable.getSort());
    }

    @Test
    void testNewPageableWhenPageIsNegative() {
        assertThrows(
            IllegalArgumentException.class,
            () -> PageUtils.newPageable(-1, null, 10, null, Allocation.SORTABLE_FIELDS)
        );
    }
}
