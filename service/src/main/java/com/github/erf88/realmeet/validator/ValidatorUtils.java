package com.github.erf88.realmeet.validator;

import static com.github.erf88.realmeet.validator.ValidatorConstants.*;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import com.github.erf88.realmeet.exception.InvalidRequestException;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ValidatorUtils {

    public static void throwOnError(ValidationErrors validationErrors) {
        if (validationErrors.hasErrors()) {
            throw new InvalidRequestException(validationErrors);
        }
    }

    public static boolean validateRequired(String field, String fieldName, ValidationErrors validationErrors) {
        if(isBlank(field)) {
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
        if(!isBlank(field) && field.trim().length() > maxLength) {
            validationErrors.add(fieldName, fieldName.concat(EXCEEDS_MAX_LENGTH));
            return false;
        }
        return true;
    }

    public static boolean validateMaxValue(Integer field, String fieldName, int maxValue, ValidationErrors validationErrors) {
        if(!isNull(field) && field > maxValue) {
            validationErrors.add(fieldName, fieldName.concat(EXCEEDS_MAX_VALUE));
            return false;
        }
        return true;
    }

    public static boolean validateMinValue(Integer field, String fieldName, int minValue, ValidationErrors validationErrors) {
        if(!isNull(field) && field < minValue) {
            validationErrors.add(fieldName, fieldName.concat(BELOW_MIN_VALUE));
            return false;
        }
        return true;
    }


}
