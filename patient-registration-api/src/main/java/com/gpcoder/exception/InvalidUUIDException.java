package com.gpcoder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUUIDException extends RuntimeException {

    public InvalidUUIDException(String message) {
        super(message);
    }

    public InvalidUUIDException(String message, Throwable cause) {
        super(message, cause);
    }
}