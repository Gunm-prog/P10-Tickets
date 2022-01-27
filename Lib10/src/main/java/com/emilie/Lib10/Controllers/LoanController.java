package com.emilie.Lib10.Controllers;

import com.emilie.Lib10.Exceptions.*;
import com.emilie.Lib10.Models.Dtos.LoanDto;
import com.emilie.Lib10.Models.Dtos.ReservationDto;
import com.emilie.Lib10.Models.Dtos.UserDto;
import com.emilie.Lib10.Services.JavaMailSenderService;
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

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

@Slf4j
@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;
    private final ReservationService reservationService;
    private final UserService userService;
    private final JavaMailSenderService javaMailSenderService;

    @Autowired
    public LoanController(
            LoanService loanService,
            ReservationService reservationService,
            UserService userService,
            JavaMailSenderService javaMailSenderService
    ) {
        this.loanService=loanService;
        this.reservationService=reservationService;
        this.userService=userService;
        this.javaMailSenderService=javaMailSenderService;
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
            LoanDto loanDto=loanService.findById( id );

            //check if a reservation need to be pass active
            try{
                ReservationDto reservationDto = reservationService.getNextReservationForBook( loanDto.getCopyDto().getBookDto() );

                reservationService.activeReservation( reservationDto );

                //send activeReservationMail
                javaMailSenderService.sendActiveReservationMail(reservationDto);
                log.info( "activeReservationMail successfully send for " + reservationDto.getUserDto().getUserId() );

                //if not found nextReservation, do nothing
            }catch (NotFoundException ignoredException){}

            LoanDto loanDtoReturned = loanService.returnLoan( id );
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

    @ApiOperation(value="send recovery mail for loan by id")
    @PostMapping("/sendRecoveryMails")
    public ResponseEntity<?> sendRecoveryMail(){

        try{
          // List<LoanDto> delayLoanList = loanService.findDelay();
         //   LoanDto delayedLoan = loanService.findById( id );
            List<UserDto> userWithDelayedLoans = userService.findUsersWithDelayedLoans();

            for(UserDto userDto : userWithDelayedLoans){
                javaMailSenderService.sendRecoveryMail( userDto );
                sleep(500);
            }

            return new ResponseEntity<>( "recovery emails send", HttpStatus.OK );
        }catch (Exception e){
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }
    }

}
