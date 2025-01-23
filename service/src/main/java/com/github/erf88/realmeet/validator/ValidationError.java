package com.github.erf88.realmeet.validator;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ValidationError {
    private final String field;
    private final String errorCode;
}
