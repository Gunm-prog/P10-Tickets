package com.emilie.Lib10.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Impossible delete reservation")
public class ImpossibleDeleteReservationException extends RuntimeException {
    private static final long serialVersionUID=1L;

    public ImpossibleDeleteReservationException(String errorMessage) {
        super( errorMessage );
    }

}


