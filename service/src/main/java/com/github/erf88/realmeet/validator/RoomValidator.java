package com.github.erf88.realmeet.validator;

import static com.github.erf88.realmeet.validator.ValidatorConstants.*;
import static com.github.erf88.realmeet.validator.ValidatorUtils.*;

import com.github.erf88.realmeet.api.model.CreateRoomDTO;
import com.github.erf88.realmeet.domain.repository.RoomRepository;
import com.github.erf88.realmeet.exception.InvalidRequestException;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoomValidator {
    private final RoomRepository roomRepository;

    public void validate(CreateRoomDTO createRoomDTO) {
        ValidationErrors validationErrors = new ValidationErrors();

        if(
            validateName(createRoomDTO.getName(), validationErrors) &&
            validateSeats(createRoomDTO.getSeats(), validationErrors)
        ) {
            validateNameDuplicate(createRoomDTO.getName(), validationErrors);
        }

        throwOnError(validationErrors);
    }

    private static boolean validateName(String name, ValidationErrors validationErrors) {
        return (
            validateRequired(name, ROOM_NAME, validationErrors) &&
            validateMaxLength(name, ROOM_NAME, ROOM_NAME_MAX_LENGTH, validationErrors)
        );
    }

    private static boolean validateSeats(Integer seats, ValidationErrors validationErrors) {
        return (
            validateRequired(seats, ROOM_SEATS, validationErrors) &&
            validateMinValue(seats, ROOM_SEATS, ROOM_SEATS_MIN_VALUE, validationErrors) &&
            validateMaxValue(seats, ROOM_SEATS, ROOM_SEATS_MAX_VALUE, validationErrors)
        );
    }

    private void validateNameDuplicate(String name, ValidationErrors validationErrors) {
        roomRepository.findByNameAndActive(name, Boolean.TRUE)
            .ifPresent(__ -> validationErrors.add(ROOM_NAME, ROOM_NAME.concat(DUPLICATE)));
    }
}
