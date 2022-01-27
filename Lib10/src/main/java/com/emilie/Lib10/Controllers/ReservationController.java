package com.emilie.Lib10.Controllers;

import com.emilie.Lib10.Exceptions.*;
import com.emilie.Lib10.Models.Dtos.ReservationDto;
import com.emilie.Lib10.Models.Dtos.UserDto;
import com.emilie.Lib10.Services.JavaMailSenderService;
import com.emilie.Lib10.Services.contract.ReservationService;
import com.emilie.Lib10.Services.contract.UserService;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/reservations")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;
    private final JavaMailSenderService javaMailSenderService;
    private final UserService userService;

    @Autowired
    public ReservationController(ReservationService reservationService, JavaMailSenderService javaMailSenderService, UserService userService) {
        this.reservationService=reservationService;
        this.javaMailSenderService = javaMailSenderService;
        this.userService=userService;
    }

    @ApiOperation(value="Create reservation and save it in database")
    @PostMapping("/new")
    public ResponseEntity<?> save(@RequestBody ReservationDto reservationDto) {
        try {
            //get user form jwt
            UserDto loggedUser = userService.getLoggedUser();
            reservationService.haveAccess( loggedUser, reservationDto );

            reservationDto = reservationService.create(reservationDto);
            log.info( "Reservation " + reservationDto.getId() + " have been created" );

            return new ResponseEntity<>( reservationDto, HttpStatus.CREATED );
        } catch (UserNotFoundException | BookNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch ( MaxResaAtteintException | UnauthorizedException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.UNAUTHORIZED )
                    .body( e.getMessage() );
        } catch (ReservationAlreadyExistException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.CONFLICT )
                    .body( e.getMessage() );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }
    }

    @ApiOperation(value="delete a reservation in database")
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelReservation(HttpServletRequest request, @PathVariable(value="id") Long id){

        try{
            UserDto loggedUser = userService.getLoggedUser();

            ReservationDto reservationDto = reservationService.findById( id );

            reservationService.haveAccess(loggedUser, reservationDto);

            reservationService.deleteById( id );

            log.info( "Reservation " + id + " have been canceled" );

            //if the canceled reservation was active, we need to activate the potential next reservation
            if(reservationDto.isActive()){
                try {
                    ReservationDto nextReservationDto = reservationService.getNextReservationForBook(reservationDto.getBookDto());

                    reservationService.activeReservation( nextReservationDto );

                    //send activeReservationMail
                    javaMailSenderService.sendActiveReservationMail(nextReservationDto);
                    log.info( "activeReservationMail successfully send for " + nextReservationDto.getUserDto().getUserId() );

                }catch (NotFoundException ignoredException){}
            }

            return ResponseEntity
                    .status( HttpStatus.OK )
                    .body( "reservation " + id + " have been canceled" );
        }catch (UserNotFoundException | BookNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (UnauthorizedException e) {
            log.warn( e.getMessage(), e );
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
}
