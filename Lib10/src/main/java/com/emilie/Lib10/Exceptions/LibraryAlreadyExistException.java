package com.emilie.Lib10.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LibraryAlreadyExistException extends RuntimeException {
    public LibraryAlreadyExistException(String message) {
        super( message );
    }
}
