package com.emilie.Lib10.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.emilie.Lib10.Config.JwtProperties;
import com.emilie.Lib10.Config.JwtTokenUtil;
import com.emilie.Lib10.Exceptions.*;
import com.emilie.Lib10.Models.Dtos.BookDto;
import com.emilie.Lib10.Models.Dtos.LoanDto;
import com.emilie.Lib10.Models.Dtos.ReservationDto;
import com.emilie.Lib10.Models.Dtos.UserDto;
import com.emilie.Lib10.Models.Entities.Reservation;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservations")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;

    @Autowired
    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService=reservationService;
        this.userService=userService;
    }

    @ApiOperation(value="Create reservation and save it in database")
    @PostMapping("/new")
    public ResponseEntity<?> save(@RequestBody ReservationDto reservationDto) {
        try {
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
