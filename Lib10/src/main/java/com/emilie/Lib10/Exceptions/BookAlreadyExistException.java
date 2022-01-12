package com.emilie.Lib10.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Book already exists")
public class BookAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID=1L;

    public BookAlreadyExistException(String errorMessage) {
        super( errorMessage );
    }
}
