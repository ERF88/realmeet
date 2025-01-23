package com.github.erf88.realmeet.exception;

import com.github.erf88.realmeet.validator.ValidationError;
import com.github.erf88.realmeet.validator.ValidationErrors;
import java.util.List;
import lombok.Getter;

@Getter
public class InvalidRequestException extends RuntimeException {
    private final ValidationErrors validationErrors;

    public InvalidRequestException(ValidationErrors validationErrors) {
        super(validationErrors.toString());
        this.validationErrors = validationErrors;
    }

    public InvalidRequestException(ValidationError validationError) {
        this(new ValidationErrors().add(validationError));
    }
}
