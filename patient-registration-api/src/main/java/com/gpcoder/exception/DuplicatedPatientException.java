package com.gpcoder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatedPatientException extends RuntimeException {

    public DuplicatedPatientException(String message) {
        super(message);
    }

    public DuplicatedPatientException(String message, Throwable cause) {
        super(message, cause);
    }
}