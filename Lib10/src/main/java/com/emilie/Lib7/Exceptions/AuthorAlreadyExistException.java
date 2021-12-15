package com.emilie.Lib7.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Author already exists")
public class AuthorAlreadyExistException extends RuntimeException {
    private static final long serialVersionUID=1L;

    public AuthorAlreadyExistException(String errorMessage) {
        super( errorMessage );
    }
}
