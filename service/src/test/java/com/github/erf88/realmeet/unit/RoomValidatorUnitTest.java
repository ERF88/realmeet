package com.github.erf88.realmeet.unit;

import static com.github.erf88.realmeet.utils.TestConstants.DEFAULT_ROOM_NAME;
import static com.github.erf88.realmeet.utils.TestDataCreator.newCreateRoomDTO;
import static com.github.erf88.realmeet.validator.ValidatorConstants.*;
import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.github.erf88.realmeet.core.BaseUnitTest;
import com.github.erf88.realmeet.domain.entity.Room;
import com.github.erf88.realmeet.domain.repository.RoomRepository;
import com.github.erf88.realmeet.exception.InvalidRequestException;
import com.github.erf88.realmeet.validator.RoomValidator;
import com.github.erf88.realmeet.validator.ValidationError;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class RoomValidatorUnitTest extends BaseUnitTest {
    private RoomValidator victim;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    public void setupEach() {
        victim = new RoomValidator(roomRepository);
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

    @Test
    void testValidateWhenRoomNameExceedsLength() {
        InvalidRequestException exception = assertThrows(
            InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO().name(rightPad("x", ROOM_NAME_MAX_LENGTH + 1, "x"))));
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(new ValidationError(ROOM_NAME, ROOM_NAME.concat(EXCEEDS_MAX_LENGTH)), exception.getValidationErrors().getError(0));
    }

    @Test
    void testValidateWhenRoomSeatsAreMissing() {
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO().seats(null)));
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(new ValidationError(ROOM_SEATS, ROOM_SEATS.concat(MISSING)), exception.getValidationErrors().getError(0));
    }

    @Test
    void testValidateWhenRoomSeatsAreLessThanMinValue() {
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO().seats(ROOM_SEATS_MIN_VALUE - 1)));
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(new ValidationError(ROOM_SEATS, ROOM_SEATS.concat(BELOW_MIN_VALUE)), exception.getValidationErrors().getError(0));
    }

    @Test
    void testValidateWhenRoomSeatsAreGreaterThanmaxValue() {
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO().seats(ROOM_SEATS_MAX_VALUE + 1)));
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(new ValidationError(ROOM_SEATS, ROOM_SEATS.concat(EXCEEDS_MAX_VALUE)), exception.getValidationErrors().getError(0));
    }

    @Test
    void testValidateWhenRoomNameDuplicate() {
        given(roomRepository.findByNameAndActive(DEFAULT_ROOM_NAME, true)).willReturn(Optional.of(Room.newBuilder().build()));

        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO()));
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(new ValidationError(ROOM_NAME, ROOM_NAME.concat(DUPLICATE)), exception.getValidationErrors().getError(0));
    }
}