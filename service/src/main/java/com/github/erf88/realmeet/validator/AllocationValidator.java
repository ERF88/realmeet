package com.github.erf88.realmeet.validator;

import static com.github.erf88.realmeet.util.DateUtils.now;
import static com.github.erf88.realmeet.validator.ValidatorConstants.*;
import static com.github.erf88.realmeet.validator.ValidatorUtils.*;

import com.github.erf88.realmeet.api.model.CreateAllocationDTO;
import com.github.erf88.realmeet.api.model.UpdateAllocationDTO;
import com.github.erf88.realmeet.api.model.UpdateRoomDTO;
import com.github.erf88.realmeet.domain.repository.AllocationRepository;
import java.time.Duration;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AllocationValidator {
    private final AllocationRepository allocationRepository;

    public void validate(CreateAllocationDTO createAllocationDTO) {
        ValidationErrors validationErrors = new ValidationErrors();
        validateSubject(createAllocationDTO.getSubject(), validationErrors);
        validateEmployeeName(createAllocationDTO.getEmployeeName(), validationErrors);
        validateEmployeeEmail(createAllocationDTO.getEmployeeEmail(), validationErrors);
        validateDates(createAllocationDTO.getStartAt(), createAllocationDTO.getEndAt(), validationErrors);
        throwOnError(validationErrors);
    }

    public void validate(Long id, UpdateAllocationDTO updateAllocationDTO) {
        ValidationErrors validationErrors = new ValidationErrors();
        validateRequired(id, ALLOCATION_ID, validationErrors);
        validateSubject(updateAllocationDTO.getSubject(), validationErrors);
        validateDates(updateAllocationDTO.getStartAt(), updateAllocationDTO.getEndAt(), validationErrors);
        throwOnError(validationErrors);
    }

    private static void validateSubject(String subject, ValidationErrors validationErrors) {
        validateRequired(subject, ALLOCATION_SUBJECT, validationErrors);
        validateMaxLength(subject, ALLOCATION_SUBJECT, ALLOCATION_SUBJECT_MAX_LENGTH, validationErrors);
    }

    private static void validateEmployeeName(String employeeName, ValidationErrors validationErrors) {
        validateRequired(employeeName, ALLOCATION_EMPLOYEE_NAME, validationErrors);
        validateMaxLength(
            employeeName,
            ALLOCATION_EMPLOYEE_NAME,
            ALLOCATION_EMPLOYEE_NAME_MAX_LENGTH,
            validationErrors
        );
    }

    private static void validateEmployeeEmail(String employeeEmail, ValidationErrors validationErrors) {
        validateRequired(employeeEmail, ALLOCATION_EMPLOYEE_EMAIL, validationErrors);
        validateMaxLength(
            employeeEmail,
            ALLOCATION_EMPLOYEE_EMAIL,
            ALLOCATION_EMPLOYEE_EMAIL_MAX_LENGTH,
            validationErrors
        );
    }

    private void validateDates(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErrors validationErrors) {
        if (validateDatesPresent(startAt, endAt, validationErrors)) {
            validateDatesOrdering(startAt, endAt, validationErrors);
            validateDatesInTheFuture(startAt, validationErrors);
            validateDuration(startAt, endAt, validationErrors);
        }
    }

    private boolean validateDatesPresent(
        OffsetDateTime startAt,
        OffsetDateTime endAt,
        ValidationErrors validationErrors
    ) {
        return (
            validateRequired(startAt, ALLOCATION_START_AT, validationErrors) &&
            validateRequired(endAt, ALLOCATION_END_AT, validationErrors)
        );
    }

    private void validateDatesOrdering(
        OffsetDateTime startAt,
        OffsetDateTime endAt,
        ValidationErrors validationErrors
    ) {
        if (startAt.isEqual(endAt) || startAt.isAfter(endAt)) {
            validationErrors.add(ALLOCATION_START_AT, ALLOCATION_START_AT.concat(INCONSISTENT));
        }
    }

    private void validateDatesInTheFuture(OffsetDateTime date, ValidationErrors validationErrors) {
        if (date.isBefore(now())) {
            validationErrors.add(ALLOCATION_START_AT, ALLOCATION_START_AT.concat(IN_THE_PAST));
        }
    }

    private void validateDuration(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErrors validationErrors) {
        if (Duration.between(startAt, endAt).getSeconds() > ALLOCATION_MAX_DURATION_SECONDS) {
            validationErrors.add(ALLOCATION_END_AT, ALLOCATION_END_AT.concat(EXCEEDS_DURATION));
        }
    }

    private void validateIfTimeAvailable(
        OffsetDateTime startAt,
        OffsetDateTime endAt,
        ValidationErrors validationErrors
    ) {
        //TODO
    }
}
