package com.github.erf88.realmeet.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity) {
        super("'%s' not found".formatted(entity));
    }
}
