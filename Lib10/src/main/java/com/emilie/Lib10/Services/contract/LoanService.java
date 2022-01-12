package com.emilie.Lib10.Services.contract;

import com.emilie.Lib10.Exceptions.LoanAlreadyExistsException;
import com.emilie.Lib10.Exceptions.LoanNotFoundException;
import com.emilie.Lib10.Exceptions.UnauthorizedException;
import com.emilie.Lib10.Models.Dtos.LoanDto;
import com.emilie.Lib10.Models.Dtos.UserDto;

import java.util.List;

public interface LoanService {

    LoanDto findById(Long id) throws LoanNotFoundException;

    LoanDto save(LoanDto loanDto) throws LoanAlreadyExistsException;

    List<LoanDto> findLoansByUserId(Long userId);

    LoanDto update(LoanDto loanDto);

    void deleteById(Long id) throws LoanNotFoundException;

    List<LoanDto> findAll();

    LoanDto extendLoan(Long loanId);

    List<LoanDto> findDelay();

    LoanDto returnLoan(Long loanId) throws LoanNotFoundException;

    void haveAccess(UserDto loggedUser, LoanDto loanDto) throws UnauthorizedException;
}
