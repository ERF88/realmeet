package com.github.erf88.realmeet.validator;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = false)
public record ValidationError(String field, String errorCode) {
}
