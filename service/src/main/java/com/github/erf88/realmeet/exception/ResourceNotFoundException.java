package com.github.erf88.realmeet.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String entity, long id) {
        super("%s not found: %d".formatted(entity, id));
    }

}
