package com.github.erf88.realmeet.unit;

import static com.github.erf88.realmeet.util.DateUtils.now;
import static com.github.erf88.realmeet.utils.TestConstants.DEFAULT_ALLOCATION_ID;
import static com.github.erf88.realmeet.utils.TestDataCreator.newUpdateAllocationDTO;
import static com.github.erf88.realmeet.validator.ValidatorConstants.*;
import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.erf88.realmeet.core.BaseUnitTest;
import com.github.erf88.realmeet.domain.repository.AllocationRepository;
import com.github.erf88.realmeet.exception.InvalidRequestException;
import com.github.erf88.realmeet.validator.AllocationValidator;
import com.github.erf88.realmeet.validator.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class AllocationUpdateValidatorUnitTest extends BaseUnitTest {
    private AllocationValidator victim;

    @Mock
    private AllocationRepository allocationRepository;

    @BeforeEach
    public void setupEach() {
        victim = new AllocationValidator(allocationRepository);
    }

    @Test
    void testValidateWhenAllocationIsValid() {
        victim.validate(DEFAULT_ALLOCATION_ID, newUpdateAllocationDTO());
    }

    @Test
    void testValidateWhenAllocationIdIsMissing() {
        InvalidRequestException exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(null, newUpdateAllocationDTO())
        );
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_ID, ALLOCATION_ID.concat(MISSING)),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenAllocationSubjectIsMissing() {
        InvalidRequestException exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(DEFAULT_ALLOCATION_ID, newUpdateAllocationDTO().subject(null))
        );
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_SUBJECT, ALLOCATION_SUBJECT.concat(MISSING)),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenAllocationSubjectExceedsLength() {
        InvalidRequestException exception = assertThrows(
            InvalidRequestException.class,
            () ->
                victim.validate(DEFAULT_ALLOCATION_ID, newUpdateAllocationDTO().subject(rightPad("x", ALLOCATION_SUBJECT_MAX_LENGTH + 1, "x")))
        );
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_SUBJECT, ALLOCATION_SUBJECT.concat(EXCEEDS_MAX_LENGTH)),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenAllocationStartAtIsMissing() {
        InvalidRequestException exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(DEFAULT_ALLOCATION_ID, newUpdateAllocationDTO().startAt(null))
        );
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_START_AT, ALLOCATION_START_AT.concat(MISSING)),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenAllocationEndAtIsMissing() {
        InvalidRequestException exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(DEFAULT_ALLOCATION_ID, newUpdateAllocationDTO().endAt(null))
        );
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_END_AT, ALLOCATION_END_AT.concat(MISSING)),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenAllocationDateOrderIsInvalid() {
        InvalidRequestException exception = assertThrows(
            InvalidRequestException.class,
            () ->
                victim.validate(
                    DEFAULT_ALLOCATION_ID,
                    newUpdateAllocationDTO().startAt(now().plusDays(1)).endAt(now().plusDays(1).minusMinutes(30))
                )
        );
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_START_AT, ALLOCATION_START_AT.concat(INCONSISTENT)),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenAllocationDateIsInThePast() {
        InvalidRequestException exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(
                DEFAULT_ALLOCATION_ID,
                newUpdateAllocationDTO().startAt(now().minusMinutes(30)).endAt(now().plusMinutes(30))
            )
        );
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_START_AT, ALLOCATION_START_AT.concat(IN_THE_PAST)),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenAllocationDateIntervalExceedsMaxDuration() {
        InvalidRequestException exception = assertThrows(
            InvalidRequestException.class,
            () ->
                victim.validate(
                    DEFAULT_ALLOCATION_ID,
                    newUpdateAllocationDTO()
                        .startAt(now().plusDays(1))
                        .endAt(now().plusDays(1).plusSeconds(ALLOCATION_MAX_DURATION_SECONDS + 1))
                )
        );
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_END_AT, ALLOCATION_END_AT.concat(EXCEEDS_DURATION)),
            exception.getValidationErrors().getError(0)
        );
    }
}
