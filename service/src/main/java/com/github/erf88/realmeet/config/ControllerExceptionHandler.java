package com.github.erf88.realmeet.config;

import com.github.erf88.realmeet.api.model.ResponseError;
import com.github.erf88.realmeet.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception e) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, e);
    }

    public ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, Exception e) {
        ResponseError responseError = new ResponseError()
                .status(httpStatus.getReasonPhrase())
                .code(httpStatus.value())
                .message(e.getMessage());
        return new ResponseEntity<>(responseError, httpStatus);
    }

}
