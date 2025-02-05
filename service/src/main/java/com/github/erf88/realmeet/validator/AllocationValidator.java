package com.github.erf88.realmeet.validator;

import static com.github.erf88.realmeet.validator.ValidatorConstants.*;
import static com.github.erf88.realmeet.validator.ValidatorUtils.*;
import static java.util.Objects.isNull;

import com.github.erf88.realmeet.api.model.CreateAllocationDTO;
import com.github.erf88.realmeet.api.model.CreateRoomDTO;
import com.github.erf88.realmeet.api.model.UpdateRoomDTO;
import com.github.erf88.realmeet.domain.repository.AllocationRepository;
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
        throwOnError(validationErrors);
    }

    private static void validateSubject(String subject, ValidationErrors validationErrors) {
        validateRequired(subject, ALLOCATION_SUBJECT, validationErrors);
        validateMaxLength(subject, ALLOCATION_SUBJECT, ALLOCATION_SUBJECT_MAX_LENGTH, validationErrors);
    }
}
