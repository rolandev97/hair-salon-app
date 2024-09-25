package com.tp.hair_salon_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorizedRequestException extends RuntimeException {
    public UnAuthorizedRequestException(String message) {
        super(message);
    }
}
