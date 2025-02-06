package com.github.erf88.realmeet.exception;

import static com.github.erf88.realmeet.validator.ValidatorConstants.ALLOCATION_ID;
import static com.github.erf88.realmeet.validator.ValidatorConstants.IN_THE_PAST;

import com.github.erf88.realmeet.validator.ValidationError;

public class AllocationCannotBeDeletedException extends InvalidRequestException {

    public AllocationCannotBeDeletedException() {
        super(new ValidationError(ALLOCATION_ID, ALLOCATION_ID + IN_THE_PAST));
    }

}
