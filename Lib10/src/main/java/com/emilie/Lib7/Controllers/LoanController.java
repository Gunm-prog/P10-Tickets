package com.emilie.Lib7.Controllers;

import com.emilie.Lib7.Exceptions.*;
import com.emilie.Lib7.Models.Dtos.LoanDto;
import com.emilie.Lib7.Services.contract.LoanService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@Slf4j
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService=loanService;
    }

    @ApiOperation(value="Retrieve loan by id, if registered in database")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value="id") Long id) throws LoanNotFoundException {
        try {
            LoanDto loanDto=this.loanService.findById( id );
            return new ResponseEntity<LoanDto>( loanDto, HttpStatus.OK );
        } catch (LoanNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }
    }

    @ApiOperation(value="Retrieve loan list from database")
    @GetMapping("/loanList")
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<List<LoanDto>>( this.loanService.findAll(), HttpStatus.OK );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findLoansByUserId(@PathVariable Long userId)
            throws LoanNotFoundException {
        try {
            return new ResponseEntity<List<LoanDto>>( loanService.findLoansByUserId( userId ), HttpStatus.OK );
        } catch (LoanNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }
    }

    @ApiOperation(value="Create loan and save it in database")
    @PostMapping("/newLoan")
    public ResponseEntity<String> save(@RequestBody LoanDto loanDto)
            throws UserNotFoundException, CopyNotFoundException, LoanAlreadyExistsException {
        try {
            LoanDto newLoanDto=loanService.save( loanDto );
            log.info( "Loan " + newLoanDto.getId() + " have been created" );
            return new ResponseEntity<>( "loan " + newLoanDto.getId() + " has been created", HttpStatus.CREATED );
        } catch (LoanAlreadyExistsException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.CONFLICT )
                    .body( e.getMessage() );
        } catch (UserNotFoundException | CopyNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }
    }

    @ApiOperation(value="extend loan, if registered in database")
    @PutMapping("/updateLoan")
    public ResponseEntity<?> update(@RequestBody LoanDto loanDto)
            throws LoanNotFoundException, UserNotFoundException, CopyNotFoundException {
        try {
            LoanDto loanDto1=loanService.update( loanDto );
            log.info( "Loan " + loanDto.getId() + " have been updated" );
            return new ResponseEntity<LoanDto>( loanDto1, HttpStatus.OK );
        } catch (LoanNotFoundException | UserNotFoundException | CopyNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }
    }

    @PutMapping("/extendLoan/{id}")
    public ResponseEntity<?> extendLoan(@PathVariable(value="id") Long id)
            throws LoanNotFoundException, ImpossibleExtendLoanException {
        try {
            LoanDto loanDto1=loanService.extendLoan( id );
            log.info( "Loan " + loanDto1.getId() + " have been extended" );
            return new ResponseEntity<LoanDto>( loanDto1, HttpStatus.OK );
        } catch (LoanNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (ImpossibleExtendLoanException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.FORBIDDEN )
                    .body( e.getMessage() );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }
    }


    @ApiOperation(value="Retrieve delayed loan list from database")
    @GetMapping("/delayList")
    public ResponseEntity<?> getDelayedLoans() {
        try {
            return new ResponseEntity<List<LoanDto>>( loanService.findDelay(), HttpStatus.OK );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }
    }


    @ApiOperation(value="update isReturned to true for a loan")
    @PutMapping("/return/{id}")
    public ResponseEntity<?> returnLoan(@PathVariable(value="id") Long id)
            throws LoanNotFoundException {
        try {
            LoanDto loanDtoReturned=loanService.returnLoan( id );
            log.info( "Loan " + id + " have been returned" );
            return new ResponseEntity<LoanDto>( loanDtoReturned, HttpStatus.OK );
        } catch (LoanNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }

    }


    @ApiOperation(value="Delete a loan from database and make the copy available")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLoan(@PathVariable(value="id") Long id)
            throws LoanNotFoundException, ImpossibleDeleteLoanException {
        try {
            loanService.deleteById( id );
            log.info( "loan " + id + " have been deleted " );
            return new ResponseEntity<>( "loan " + id + " have been deleted ", HttpStatus.OK );
        } catch (LoanNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (ImpossibleDeleteLoanException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.FORBIDDEN )
                    .body( e.getMessage() );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }

    }

}
