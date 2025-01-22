package com.github.erf88.realmeet.validator;

import static com.github.erf88.realmeet.validator.ValidatorConstants.EXCEEDS_MAX_LENGTH;
import static com.github.erf88.realmeet.validator.ValidatorConstants.MISSING;
import static java.util.Objects.isNull;

import com.github.erf88.realmeet.exception.InvalidRequestException;
import java.util.Objects;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ValidatorUtils {

    public static void throwOnError(ValidationErrors validationErrors) {
        if (validationErrors.hasErrors()) {
            throw new InvalidRequestException(validationErrors);
        }
    }

    public static boolean validateRequired(String field, String fieldName, ValidationErrors validationErrors) {
        if(field.isBlank()) {
            validationErrors.add(fieldName, fieldName.concat(MISSING));
            return false;
        }
        return true;
    }

    public static boolean validateRequired(Object field, String fieldName, ValidationErrors validationErrors) {
        if(isNull(field)) {
            validationErrors.add(fieldName, fieldName.concat(MISSING));
            return false;
        }
        return true;
    }

    public static boolean validateMaxLength(String field, String fieldName, int maxLength, ValidationErrors validationErrors) {
        if(!field.isBlank() && field.trim().length() > maxLength) {
            validationErrors.add(fieldName, fieldName.concat(EXCEEDS_MAX_LENGTH));
            return false;
        }
        return true;
    }
}
