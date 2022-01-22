package com.emilie.Lib10.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="reservation list is full for this book")
public class MaxResaAtteintException extends RuntimeException {
    private static final long serialVersionUID=1L;

    public MaxResaAtteintException(String errorMessage) {
        super( errorMessage );
    }

}