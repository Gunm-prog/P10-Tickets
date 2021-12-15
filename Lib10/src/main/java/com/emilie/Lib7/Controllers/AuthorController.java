package com.emilie.Lib7.Controllers;

import com.emilie.Lib7.Exceptions.AuthorAlreadyExistException;
import com.emilie.Lib7.Exceptions.AuthorNotFoundException;
import com.emilie.Lib7.Exceptions.ImpossibleDeleteAuthorException;
import com.emilie.Lib7.Models.Dtos.AuthorDto;
import com.emilie.Lib7.Services.contract.AuthorsService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@Slf4j
public class AuthorController {

    private final AuthorsService authorsService;


    @Autowired
    public AuthorController(AuthorsService authorsService) {

        this.authorsService=authorsService;

    }

    @ApiOperation(value="Retrieve userlist from database")
    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        try {
            List<AuthorDto> authorDtos=authorsService.findAll();
            return new ResponseEntity<List<AuthorDto>>( authorDtos, HttpStatus.OK );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }
    }

    @ApiOperation(value="Retrieve an author by its id if existing in database")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value="id") Long id)
            throws AuthorNotFoundException {
        try {
            AuthorDto authorDto=authorsService.findById( id );
            return new ResponseEntity<AuthorDto>( authorDto, HttpStatus.OK );
        } catch (AuthorNotFoundException e) {
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


    @PostMapping("/newAuthor")
    public ResponseEntity<String> save(@RequestBody AuthorDto authorDto)
            throws AuthorAlreadyExistException {
        try {
            AuthorDto newAuthorDto=authorsService.save( authorDto );
            log.info( "Author " + newAuthorDto.getAuthorId() + " has been created" );
            return new ResponseEntity<String>( "Author " + newAuthorDto.getAuthorId() + " has been created", HttpStatus.CREATED );
        } catch (AuthorAlreadyExistException e) {
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

    @PutMapping("/updateAuthor")
    public ResponseEntity<?> update(@RequestBody AuthorDto authorDto)
            throws AuthorNotFoundException {
        try {
            AuthorDto authorDto1=authorsService.update( authorDto );
            log.info( "Author " + authorDto1.getAuthorId() + " has been updated" );
            return new ResponseEntity<AuthorDto>( authorDto1, HttpStatus.OK );
        } catch (AuthorNotFoundException e) {
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value="id") Long id)
            throws AuthorNotFoundException, ImpossibleDeleteAuthorException {
        try {
            authorsService.deleteById( id );
            log.info( "Author " + id + " has been deleted" );
            return new ResponseEntity<String>( "Author " + id + " has been deleted", HttpStatus.OK );
        } catch (AuthorNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (ImpossibleDeleteAuthorException e) {
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
