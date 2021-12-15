
package com.emilie.Lib7.Controllers;

import com.emilie.Lib7.Exceptions.BookNotFoundException;
import com.emilie.Lib7.Exceptions.CopyNotFoundException;
import com.emilie.Lib7.Exceptions.ImpossibleExtendLoanException;
import com.emilie.Lib7.Exceptions.LibraryNotFoundException;
import com.emilie.Lib7.Models.Dtos.CopyDto;
import com.emilie.Lib7.Services.contract.CopyService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/copies")
@Slf4j
public class CopyController {


    private final CopyService copyService;

    @Autowired
    public CopyController(CopyService copyService) {
        this.copyService=copyService;
    }


    @ApiOperation(value="Retrieve books which are registered in database")
    @GetMapping("/search")
    public ResponseEntity<?> searchCopies(@RequestParam(value="libraryId", required=false) Long libraryId,
                                          @RequestParam(value="title", required=false) String title,
                                          @RequestParam(value="isbn", required=false) String isbn,
                                          @RequestParam(value="firstName", required=false) String firstName,
                                          @RequestParam(value="lastName", required=false) String lastName) {

        try {
            List<CopyDto> copyDtos=copyService.searchCopies( libraryId, title, isbn, firstName, lastName );
            return new ResponseEntity<List<CopyDto>>( copyDtos, HttpStatus.OK );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }

    }


    @ApiOperation(value="Retrieve copy by id, if registered in database")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value="id") Long id)
            throws CopyNotFoundException {
        try {
            CopyDto copyDto=copyService.findById( id );
            return new ResponseEntity<CopyDto>( copyDto, HttpStatus.OK );
        } catch (CopyNotFoundException e) {
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

    @ApiOperation(value="Retrieve copy list from database")
    @GetMapping("/copyList")
    public ResponseEntity<?> findAll() {
        try {
            List<CopyDto> copyDtos=copyService.findAll();
            return new ResponseEntity<List<CopyDto>>( copyDtos, HttpStatus.OK );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }
    }


    @ApiOperation(value="Create a copy and save it in database")
    @PostMapping("/newCopy")
    public ResponseEntity<String> save(@RequestBody CopyDto copyDto)
            throws BookNotFoundException, LibraryNotFoundException {
        try {
            CopyDto newCopyDto=copyService.save( copyDto );
            log.info( "Copy " + newCopyDto.getId() + " has been created" );
            return new ResponseEntity<>( "Copy " + newCopyDto.getId() + " has been created", HttpStatus.CREATED );
        } catch (BookNotFoundException | LibraryNotFoundException e) {
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

    @ApiOperation(value="update copy saving modifications in database")
    @PutMapping("/updateCopy")
    public ResponseEntity<?> update(@RequestBody CopyDto copyDto)
            throws CopyNotFoundException, BookNotFoundException, LibraryNotFoundException {
        try {
            CopyDto copyDto1=copyService.update( copyDto );
            log.info( "Copy " + copyDto1.getId() + " has been updated" );
            return new ResponseEntity<CopyDto>( copyDto1, HttpStatus.OK );
        } catch (CopyNotFoundException | BookNotFoundException | LibraryNotFoundException e) {
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

    @ApiOperation(value="delete copy from database by id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value="id") Long id)
            throws CopyNotFoundException, ImpossibleExtendLoanException {
        try {
            copyService.deleteById( id );
            log.info( "Copy " + id + "has been deleted" );
            return ResponseEntity
                    .status( HttpStatus.OK )
                    .body( "the copy " + id + " have been deleted" );
        } catch (CopyNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (ImpossibleExtendLoanException e) {
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

