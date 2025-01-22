package com.github.erf88.realmeet.validator;

import static com.github.erf88.realmeet.validator.ValidatorConstants.ROOM_NAME;
import static com.github.erf88.realmeet.validator.ValidatorConstants.ROOM_NAME_MAX_LENGTH;
import static com.github.erf88.realmeet.validator.ValidatorUtils.*;

import com.github.erf88.realmeet.api.model.CreateRoomDTO;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {
    public void validate(CreateRoomDTO createRoomDTO) {
        ValidationErrors validationErrors = new ValidationErrors();

        validateRequired(createRoomDTO.getName(), ROOM_NAME, validationErrors);
        validateMaxLength(createRoomDTO.getName(), ROOM_NAME, ROOM_NAME_MAX_LENGTH, validationErrors);
        throwOnError(validationErrors);
    }
}
