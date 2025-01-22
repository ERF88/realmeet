package com.github.erf88.realmeet.validator;

import com.github.erf88.realmeet.exception.InvalidRequestException;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ValidatorUtils {

    public static void throwOnError(ValidationErrors validationErrors) {
        if (validationErrors.hasErrors()) {
            throw new InvalidRequestException(validationErrors);
        }
    }
}
