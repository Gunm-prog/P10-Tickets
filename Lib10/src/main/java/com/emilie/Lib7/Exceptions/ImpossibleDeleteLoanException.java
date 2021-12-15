package com.emilie.Lib7.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Impossible delete loan")
public class ImpossibleDeleteLoanException extends RuntimeException {
    private static final long serialVersionUID=1L;

    public ImpossibleDeleteLoanException(String errorMessage) {
        super( errorMessage );
    }

}


