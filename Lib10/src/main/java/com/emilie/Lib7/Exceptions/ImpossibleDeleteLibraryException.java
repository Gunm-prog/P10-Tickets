package com.emilie.Lib7.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Impossible delete library")
public class ImpossibleDeleteLibraryException extends RuntimeException {
    private static final long serialVersionUID=1L;

    public ImpossibleDeleteLibraryException(String errorMessage) {
        super( errorMessage );
    }

}


