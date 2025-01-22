package com.github.erf88.realmeet.exception;

import com.github.erf88.realmeet.validator.ValidationErrors;
import lombok.Getter;

@Getter
public class InvalidRequestException extends RuntimeException {

    private final ValidationErrors validationErrors;

    public InvalidRequestException(ValidationErrors validationErrors) {
        super(validationErrors.toString());
        this.validationErrors = validationErrors;
    }
}
