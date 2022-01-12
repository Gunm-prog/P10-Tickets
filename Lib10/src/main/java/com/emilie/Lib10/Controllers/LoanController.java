package com.emilie.Lib10.Controllers;

import com.emilie.Lib10.Exceptions.*;
import com.emilie.Lib10.Models.Dtos.LoanDto;
import com.emilie.Lib10.Models.Dtos.ReservationDto;
import com.emilie.Lib10.Models.Dtos.UserDto;
import com.emilie.Lib10.Services.contract.LoanService;
import com.emilie.Lib10.Services.contract.ReservationService;
import com.emilie.Lib10.Services.contract.UserService;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
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
    private final ReservationService reservationService;
    private final UserService userService;

    @Autowired
    public LoanController(LoanService loanService, ReservationService reservationService, UserService userService) {
        this.loanService=loanService;
        this.reservationService=reservationService;
        this.userService=userService;
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

            UserDto loggedUser = userService.getLoggedUser();
            loanService.haveAccess( loggedUser, loanDto );

            List <ReservationDto> reservationsList = reservationService.getReservationsByBookId( loanDto.getCopyDto().getBookDto().getBookId() );

            //if book's reservation exist
            if(!reservationsList.isEmpty()){
                try{
                    //found userReservation if it's active or exist
                    ReservationDto userReservationDto = reservationService.haveActiveReservationForUser( loanDto.getUserDto(), loanDto.getCopyDto().getBookDto() );

                    //delete the finished reservation
                    reservationService.deleteById( userReservationDto.getId() );

                    try{//active potential next reservation for this book
                        ReservationDto nextReservation = reservationService.getNextReservationForBook( loanDto.getCopyDto().getBookDto() );
                        reservationService.activeReservation( nextReservation );

                        //todo send email

                        //if nextReservation not found do nothing
                    }catch(NotFoundException ignoredException){}

                }catch(NotFoundException e){//if user can't loan the book because it's reserved.
                    log.info( "loan can't be accept, the book is reserved" );
                    return ResponseEntity
                            .status( HttpStatus.UNAUTHORIZED )
                            .body( "loan can't be accept, the book is reserved" );
                }
            }

            LoanDto newLoanDto=loanService.save( loanDto );
            log.info( "Loan " + newLoanDto.getId() + " have been created" );
            return new ResponseEntity<>( "loan " + newLoanDto.getId() + " has been created", HttpStatus.CREATED );
        } catch (LoanAlreadyExistsException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.CONFLICT )
                    .body( e.getMessage() );
        } catch ( UserNotFoundException | CopyNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch ( UnauthorizedException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.UNAUTHORIZED )
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

            //retrieve the loan in database
            LoanDto loanDtoReturned=loanService.findById( id );

            //check if a reservation need to be pass active
            try{
                ReservationDto reservationDto = reservationService.getNextReservationForBook( loanDtoReturned.getCopyDto().getBookDto() );

                reservationService.activeReservation( reservationDto );

                //todo sendEmail

                //if not found nextReservation, do nothing
            }catch (NotFoundException ignoredException){}

            loanService.returnLoan( id );
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
