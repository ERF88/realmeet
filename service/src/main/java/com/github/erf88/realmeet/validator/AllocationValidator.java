package com.github.erf88.realmeet.validator;

import static com.github.erf88.realmeet.validator.ValidatorConstants.*;
import static com.github.erf88.realmeet.validator.ValidatorUtils.*;
import static java.util.Objects.isNull;

import com.github.erf88.realmeet.api.model.CreateAllocationDTO;
import com.github.erf88.realmeet.api.model.CreateRoomDTO;
import com.github.erf88.realmeet.api.model.UpdateRoomDTO;
import com.github.erf88.realmeet.domain.repository.AllocationRepository;
import java.time.OffsetDateTime;
import java.util.Objects;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

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

    private static boolean validateSubject(String subject, ValidationErrors validationErrors) {
        return (
            validateRequired(subject, ALLOCATION_SUBJECT, validationErrors) &&
            validateMaxLength(subject, ALLOCATION_SUBJECT, ALLOCATION_SUBJECT_MAX_LENGTH, validationErrors)
        );
    }

    private static boolean validateEmployeeName(String employeeName, ValidationErrors validationErrors) {
        return (
            validateRequired(employeeName, ALLOCATION_EMPLOYEE_NAME, validationErrors) &&
            validateMaxLength(employeeName, ALLOCATION_EMPLOYEE_NAME, ALLOCATION_EMPLOYEE_NAME_MAX_LENGTH, validationErrors)
        );
    }
    private static boolean validateEmployeeEmail(String employeeEmail, ValidationErrors validationErrors) {
        return (
            validateRequired(employeeEmail, ALLOCATION_EMPLOYEE_EMAIL, validationErrors) &&
            validateMaxLength(employeeEmail, ALLOCATION_EMPLOYEE_EMAIL, ALLOCATION_EMPLOYEE_EMAIL_MAX_LENGTH, validationErrors)
        );
    }

    private void validateDates(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErrors validationErrors) {
        if(validateDatesPresent(startAt, endAt, validationErrors)) {
            validateDatesOrdering(startAt, endAt, validationErrors);
        }
    }

    private boolean validateDatesPresent(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErrors validationErrors) {
        return (
            validateRequired(startAt, ALLOCATION_START_AT, validationErrors) &&
            validateRequired(endAt, ALLOCATION_END_AT, validationErrors)
        );
    }

    private boolean validateDatesOrdering(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErrors validationErrors) {
        if(startAt.isEqual(endAt) || startAt.isAfter(endAt)) {
            validationErrors.add(ALLOCATION_START_AT, INCONSISTENT);
            return false;
        }
        return true;
    }
}
