package com.emilie.Lib10.Controllers;


import com.emilie.Lib10.Config.Jwt.JwtTokenUtil;
import com.emilie.Lib10.Exceptions.ImpossibleDeleteUserException;
import com.emilie.Lib10.Exceptions.UserNotFoundException;
import com.emilie.Lib10.Models.Dtos.ReservationDto;
import com.emilie.Lib10.Models.Dtos.UserDto;
import com.emilie.Lib10.Models.Entities.UserPrincipal;
import com.emilie.Lib10.Services.contract.ReservationService;
import com.emilie.Lib10.Services.contract.UserService;
import com.emilie.Lib10.Services.impl.UserDetailsServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ReservationService reservationService;


  //  @Autowired
  /*  public UserController(UserService userService, UserDetailsServiceImpl userDetailsServiceImpl, JwtTokenUtil jwtTokenUtil, ReservationService reservationService) {
        this.userService=userService;
        this.userDetailsServiceImpl=userDetailsServiceImpl;
        this.jwtTokenUtil=jwtTokenUtil;

        this.reservationService = reservationService;
    }*/

    @ApiOperation(value="Retrieve a user account thanks to its Id, if the user is registered in database")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value="id") Long id) throws UserNotFoundException {
        try {
            UserDto userDto=userService.findById( id );
            userDto.setPassword( "" );

            //add complementary data reservation
            for(ReservationDto reservationDto : userDto.getReservationDtos()){
                reservationDto.setMinExpectedReturnDate( reservationService.getMinExpectedReturnDate( reservationDto.getBookDto() ) );
            }

            return new ResponseEntity<UserDto>( userDto, HttpStatus.OK );
        } catch (UserNotFoundException e) {
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


    @ApiOperation("retrieve a user with token and id, if the user is registered in database")
    @GetMapping("/userAccount")
    public ResponseEntity<?> getLoggedUser() throws UserNotFoundException {
        try {
            UserDto userDto=userService.getLoggedUser();
            userDto.setPassword( "" );

            for(ReservationDto reservationDto : userDto.getReservationDtos()){
                reservationDto.setMinExpectedReturnDate( reservationService.getMinExpectedReturnDate( reservationDto.getBookDto() ) );
            }

            return new ResponseEntity<UserDto>( userDto, HttpStatus.OK );
        } catch (UserNotFoundException e) {
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


    @ApiOperation(value="Retrieve user list from the database")
    @GetMapping("/userList")
    public ResponseEntity<?> findAll() {
        try {
            List<UserDto> userDtos=userService.findAll();
            return new ResponseEntity<List<UserDto>>( userDtos, HttpStatus.OK );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }
    }


    @ApiOperation(value="update user saving modifications in database and return a newJwtToken")
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto)
            throws UserNotFoundException {
        try {
            UserDto userDto1=userService.update( userDto );
            UserPrincipal userPrincipal=userDetailsServiceImpl.loadUserByUsername( userDto1.getEmail() );

            return new ResponseEntity<String>( jwtTokenUtil.generateToken( userPrincipal ), HttpStatus.OK );
        } catch (UserNotFoundException e) {
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


    @ApiOperation(value="delete user from database by id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(value="id") Long id)
            throws UserNotFoundException, ImpossibleDeleteUserException {
        try {
            userService.deleteById( id );
            log.info( "user " + id + " has been deleted" );
            return new ResponseEntity<>( "user " + id + " has been deleted", HttpStatus.OK );
        } catch (UserNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (ImpossibleDeleteUserException e) {
            log.info( e.getMessage() );
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

}
