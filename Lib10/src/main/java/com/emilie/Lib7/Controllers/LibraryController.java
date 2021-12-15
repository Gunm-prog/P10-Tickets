
package com.emilie.Lib7.Controllers;


import com.emilie.Lib7.Exceptions.ImpossibleDeleteLibraryException;
import com.emilie.Lib7.Exceptions.LibraryAlreadyExistException;
import com.emilie.Lib7.Exceptions.LibraryNotFoundException;
import com.emilie.Lib7.Models.Dtos.CopyDto;
import com.emilie.Lib7.Models.Dtos.LibraryDto;
import com.emilie.Lib7.Services.contract.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/libraries")
@Slf4j
public class LibraryController {

    private final LibraryService libraryService;


    @Autowired
    public LibraryController(LibraryService libraryService) {

        this.libraryService=libraryService;

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value="id") Long id) throws LibraryNotFoundException {
        try {
            LibraryDto libraryDto=libraryService.findById( id );
            return new ResponseEntity<LibraryDto>( libraryDto, HttpStatus.OK );
        } catch (LibraryNotFoundException e) {
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

    @GetMapping("/libraryList")
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<List<LibraryDto>>( this.libraryService.findAll(), HttpStatus.OK );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }
    }

    @GetMapping("/{id}/copyCatalog")
    public ResponseEntity<?> findCopiesByLibraryId(@PathVariable(value="id") Long id) {
        try {
            return new ResponseEntity<Set<CopyDto>>( libraryService.findCopiesByLibraryId( id ), HttpStatus.OK );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }
    }

    @PostMapping("/newLibrary")
    public ResponseEntity<String> save(@RequestBody LibraryDto libraryDto)
            throws LibraryAlreadyExistException {
        try {
            libraryService.save( libraryDto );
            return ResponseEntity.status( HttpStatus.CREATED ).build();
        } catch (LibraryAlreadyExistException e) {
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

    @PutMapping("/updateLibrary")
    public ResponseEntity<?> update(@RequestBody LibraryDto libraryDto) throws LibraryNotFoundException {
        try {
            LibraryDto libraryDto1=libraryService.update( libraryDto );
            return new ResponseEntity<LibraryDto>( libraryDto1, HttpStatus.OK );
        } catch (LibraryNotFoundException e) {
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
            throws LibraryNotFoundException, ImpossibleDeleteLibraryException {
        try {
            libraryService.deleteById( id );
            return ResponseEntity.status( HttpStatus.OK ).build();
        } catch (LibraryNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (ImpossibleDeleteLibraryException e) {
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

