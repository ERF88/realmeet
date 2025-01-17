package com.github.erf88.realmeet.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ResponseEntityUtils {

    public static <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

}
