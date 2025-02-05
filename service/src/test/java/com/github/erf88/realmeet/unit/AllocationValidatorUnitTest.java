package com.github.erf88.realmeet.unit;

import static com.github.erf88.realmeet.util.DateUtils.now;
import static com.github.erf88.realmeet.utils.TestConstants.DEFAULT_ROOM_NAME;
import static com.github.erf88.realmeet.utils.TestDataCreator.newCreateAllocationDTO;
import static com.github.erf88.realmeet.utils.TestDataCreator.newCreateRoomDTO;
import static com.github.erf88.realmeet.validator.ValidatorConstants.*;
import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.github.erf88.realmeet.core.BaseUnitTest;
import com.github.erf88.realmeet.domain.entity.Room;
import com.github.erf88.realmeet.domain.repository.AllocationRepository;
import com.github.erf88.realmeet.domain.repository.RoomRepository;
import com.github.erf88.realmeet.exception.InvalidRequestException;
import com.github.erf88.realmeet.validator.AllocationValidator;
import com.github.erf88.realmeet.validator.RoomValidator;
import com.github.erf88.realmeet.validator.ValidationError;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class AllocationValidatorUnitTest extends BaseUnitTest {
    private AllocationValidator victim;

    @Mock
    private AllocationRepository allocationRepository;

    @BeforeEach
    public void setupEach() {
        victim = new AllocationValidator(allocationRepository);
    }

    @Test
    void testValidateWhenAllocationIsValid() {
        victim.validate(newCreateAllocationDTO());
    }

    @Test
    void testValidateWhenAllocationSubjectIsMissing() {
        InvalidRequestException exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(newCreateAllocationDTO().subject(null))
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
            () -> victim.validate(newCreateAllocationDTO()
                .subject(rightPad("x", ALLOCATION_SUBJECT_MAX_LENGTH + 1, "x")))
        );
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_SUBJECT, ALLOCATION_SUBJECT.concat(EXCEEDS_MAX_LENGTH)),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenAllocationEmployeeNameIsMissing() {
        InvalidRequestException exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(newCreateAllocationDTO().employeeName(null))
        );
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_EMPLOYEE_NAME, ALLOCATION_EMPLOYEE_NAME.concat(MISSING)),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenAllocationEmployeeNameExceedsLength() {
        InvalidRequestException exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(newCreateAllocationDTO()
                .employeeName(rightPad("x", ALLOCATION_EMPLOYEE_NAME_MAX_LENGTH + 1, "x")))
        );
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_EMPLOYEE_NAME, ALLOCATION_EMPLOYEE_NAME.concat(EXCEEDS_MAX_LENGTH)),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenAllocationEmployeeEmailIsMissing() {
        InvalidRequestException exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(newCreateAllocationDTO().employeeEmail(null))
        );
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_EMPLOYEE_EMAIL, ALLOCATION_EMPLOYEE_EMAIL.concat(MISSING)),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenAllocationEmployeeEmailExceedsLength() {
        InvalidRequestException exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(newCreateAllocationDTO()
                .employeeEmail(rightPad("x", ALLOCATION_EMPLOYEE_EMAIL_MAX_LENGTH + 1, "x")))
        );
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_EMPLOYEE_EMAIL, ALLOCATION_EMPLOYEE_EMAIL.concat(EXCEEDS_MAX_LENGTH)),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenAllocationStartAtIsMissing() {
        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> victim.validate(newCreateAllocationDTO().startAt(null))
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
                () -> victim.validate(newCreateAllocationDTO().endAt(null))
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
                () -> victim
                    .validate(newCreateAllocationDTO().startAt(now().plusDays(1)).endAt(now().plusDays(1).minusMinutes(30)))
        );
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
                new ValidationError(ALLOCATION_START_AT, ALLOCATION_START_AT.concat(INCONSISTENT)),
                exception.getValidationErrors().getError(0)
        );
    }
}
