package com.emilie.Lib7.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.FORBIDDEN, reason="not the current user")
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String errorMessage) {
        super( errorMessage );
    }
}


