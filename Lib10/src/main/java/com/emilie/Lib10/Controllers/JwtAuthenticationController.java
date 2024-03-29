package com.emilie.Lib10.Controllers;

import com.emilie.Lib10.Config.Jwt.JwtTokenUtil;
import com.emilie.Lib10.Exceptions.AddressNotFoundException;
import com.emilie.Lib10.Exceptions.UserAlreadyExistException;
import com.emilie.Lib10.Models.Dtos.UserDto;
import com.emilie.Lib10.Models.Dtos.UserJwtDto;
import com.emilie.Lib10.Models.Entities.UserPrincipal;
import com.emilie.Lib10.Repositories.UserRepository;
import com.emilie.Lib10.Services.impl.UserDetailsServiceImpl;
import com.emilie.Lib10.Services.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @RequestMapping(value="/authenticate", method=RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserJwtDto userJwtDto)
            throws UsernameNotFoundException, DisabledException, BadCredentialsException {

        try {
            authenticate( userJwtDto.getUsername(), userJwtDto.getPassword() );

            UserPrincipal userPrincipal=userDetailsServiceImpl.loadUserByUsername( userJwtDto.getUsername() );

            List<String> dataResponse = new ArrayList<>();
            dataResponse.add( jwtTokenUtil.generateToken( userPrincipal ) );
            
            ResponseEntity<?> testResponse = new ResponseEntity<>( HttpStatus.OK );

           // return new ResponseEntity<String>( jwtTokenUtil.generateToken( userPrincipal ), HttpStatus.OK );
            return new ResponseEntity<List<String>>( dataResponse, HttpStatus.OK );
        } catch (UsernameNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (DisabledException | BadCredentialsException e) {
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


    private void authenticate(String username, String password) throws DisabledException, BadCredentialsException {
        try {
            authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( username, password ) );
        } catch (DisabledException e) {
            throw new DisabledException( "USER_DISABLED", e );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException( "INVALID_CREDENTIALS", e );
        }
    }


    @PostMapping(value="/register/employee")
    public ResponseEntity<?> registerEmployee(@RequestBody UserDto userDto)
            throws UserAlreadyExistException, AddressNotFoundException {
        try {
            userDto.setActive( true );
            userDto.setRoles( "EMPLOYEE" );
            String hashPassword=bCryptPasswordEncoder.encode( userDto.getPassword() );
            userDto.setPassword( hashPassword );
            UserDto userDto1=userServiceImpl.save( userDto );
            userDto1.setPassword( "" );
            log.info( "Employee " + userDto1.getUserId() + " has been created" );
            return new ResponseEntity<UserDto>( userDto1, HttpStatus.CREATED );
        } catch (AddressNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (UserAlreadyExistException e) {
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

    @PostMapping(value="/register/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody UserDto userDto)
            throws UserAlreadyExistException, AddressNotFoundException {
        try {
            String hashPassword=bCryptPasswordEncoder.encode( userDto.getPassword() );

            userDto.setPassword( hashPassword );
            userDto.setActive( true );
            userDto.setRoles( "CUSTOMER" );

            UserDto userDto1=userServiceImpl.save( userDto );
            userDto1.setPassword( "" );
            log.info( "Customer " + userDto1.getUserId() + " has been created" );
            return new ResponseEntity<UserDto>( userDto1, HttpStatus.CREATED );
        } catch (AddressNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (UserAlreadyExistException e) {
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
}














