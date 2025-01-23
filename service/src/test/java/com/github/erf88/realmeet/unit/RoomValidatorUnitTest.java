package com.github.erf88.realmeet.unit;

import static com.github.erf88.realmeet.utils.TestDataCreator.newCreateRoomDTO;
import static com.github.erf88.realmeet.validator.ValidatorConstants.MISSING;
import static com.github.erf88.realmeet.validator.ValidatorConstants.ROOM_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.erf88.realmeet.core.BaseUnitTest;
import com.github.erf88.realmeet.exception.InvalidRequestException;
import com.github.erf88.realmeet.validator.RoomValidator;
import com.github.erf88.realmeet.validator.ValidationError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoomValidatorUnitTest extends BaseUnitTest {
    private RoomValidator victim;

    @BeforeEach
    public void setupEach() {
        victim = new RoomValidator();
    }

    @Test
    void testValidateWhenRoomIsValid() {
        victim.validate(newCreateRoomDTO());
    }

    @Test
    void testValidateWhenRoomNameIsMissing() {
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO().name(null)));
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(new ValidationError(ROOM_NAME, ROOM_NAME.concat(MISSING)), exception.getValidationErrors().getError(0));
    }
}
