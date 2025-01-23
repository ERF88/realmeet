package com.github.erf88.realmeet.config;

import static com.github.erf88.realmeet.util.ResponseEntityUtils.notFound;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import com.github.erf88.realmeet.api.model.ResponseError;
import com.github.erf88.realmeet.exception.InvalidRequestException;
import com.github.erf88.realmeet.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(Exception e) {
        return notFound();
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseBody
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public List<ResponseError> handleInvalidRequestException(InvalidRequestException exception) {
        return exception
            .getValidationErrors()
            .stream()
            .map(e -> new ResponseError().field(e.getField()).errorCode(e.getErrorCode()))
            .toList();
    }
}
