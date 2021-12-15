package com.emilie.Lib7.Controllers;


import com.emilie.Lib7.Config.JwtTokenUtil;
import com.emilie.Lib7.Exceptions.ImpossibleDeleteUserException;
import com.emilie.Lib7.Exceptions.UserNotFoundException;
import com.emilie.Lib7.Models.Dtos.UserDto;
import com.emilie.Lib7.Models.Entities.UserPrincipal;
import com.emilie.Lib7.Services.contract.UserService;
import com.emilie.Lib7.Services.impl.UserDetailsServiceImpl;
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

    private final UserService userService;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private final JwtTokenUtil jwtTokenUtil;


    @Autowired
    public UserController(UserService userService, UserDetailsServiceImpl userDetailsServiceImpl, JwtTokenUtil jwtTokenUtil) {
        this.userService=userService;
        this.userDetailsServiceImpl=userDetailsServiceImpl;
        this.jwtTokenUtil=jwtTokenUtil;

    }

    @ApiOperation(value="Retrieve a user account thanks to its Id, if the user is registered in database")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value="id") Long id) throws UserNotFoundException {
        try {
            UserDto userDto=userService.findById( id );
            userDto.setPassword( "" );//todo ajout recent si debud needed
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
