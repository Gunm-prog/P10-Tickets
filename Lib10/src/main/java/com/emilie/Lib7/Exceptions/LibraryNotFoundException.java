package com.emilie.Lib7.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Library Not Found")
public class LibraryNotFoundException extends RuntimeException {

    private static final long serialVersionUID=1L;

    public LibraryNotFoundException(String errorMessage) {
        super( errorMessage );
    }
}