package com.github.erf88.realmeet.exception;

import static com.github.erf88.realmeet.validator.ValidatorConstants.INVALID;
import static com.github.erf88.realmeet.validator.ValidatorConstants.ORDER_BY;

import com.github.erf88.realmeet.validator.ValidationError;

public class InvalidOrderByFieldException extends InvalidRequestException {
    public InvalidOrderByFieldException() {
        super(new ValidationError(ORDER_BY, ORDER_BY.concat(INVALID)));
    }
}
