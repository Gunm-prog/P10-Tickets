package com.emilie.Lib7.Controllers;


import com.emilie.Lib7.Exceptions.AuthorNotFoundException;
import com.emilie.Lib7.Exceptions.BookAlreadyExistException;
import com.emilie.Lib7.Exceptions.BookNotFoundException;
import com.emilie.Lib7.Exceptions.ImpossibleDeleteBookException;
import com.emilie.Lib7.Models.Dtos.BookDto;
import com.emilie.Lib7.Services.contract.BookService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Slf4j
public class BookController {


    private final BookService bookService;


    @Autowired
    public BookController(BookService bookService) {
        this.bookService=bookService;

    }


    @ApiOperation(value="Retrieve a book by id, if registered in database")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value="id") Long id)
            throws BookNotFoundException {
        try {
            BookDto bookDto=bookService.findById( id );
            return new ResponseEntity<BookDto>( bookDto, HttpStatus.OK );
        } catch (BookNotFoundException e) {
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

    @ApiOperation(value="Retrieve books which are registered in database")
    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(@RequestParam(value="libraryId", required=false) Long libraryId,
                                         @RequestParam(value="title", required=false) String title,
                                         @RequestParam(value="isbn", required=false) String isbn,
                                         @RequestParam(value="firstname", required=false) String firstName,
                                         @RequestParam(value="lastname", required=false) String lastName) {

        try {
            List<BookDto> bookDtos=bookService.searchBooks( libraryId, title, isbn, firstName, lastName );
            return new ResponseEntity<List<BookDto>>( bookDtos, HttpStatus.OK );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }

    }


    @ApiOperation(value="Retrieve the booklist which is registered in database")
    @GetMapping("/catalog")
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<List<BookDto>>( this.bookService.findAll(), HttpStatus.OK );
        } catch (Exception e) {
            log.warn( e.getMessage(), e );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( "INTERNAL_SERVER_ERROR" );
        }

    }


    @ApiOperation(value="Register a new book in database")
    @PostMapping("/newBook")
    public ResponseEntity<String> save(@RequestBody BookDto bookDto)
            throws BookAlreadyExistException, AuthorNotFoundException {
        try {
            BookDto newBookDto=bookService.save( bookDto );
            log.info( "Book " + newBookDto.getBookId() + " has been created" );
            return new ResponseEntity<String>( "Book " + newBookDto.getBookId() + " has been created", HttpStatus.CREATED );
        } catch (AuthorNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (BookAlreadyExistException e) {
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


    @ApiOperation(value="update book (like new edition???) and save modifications in database")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody BookDto bookDto, @PathVariable(value="id") Long id)
            throws BookNotFoundException {
        try {
            BookDto bookDto1=bookService.update( bookDto );
            log.info( "Book " + bookDto1.getBookId() + " has been updated" );
            return new ResponseEntity<BookDto>( bookDto1, HttpStatus.OK );
        } catch (BookNotFoundException e) {
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


    @ApiOperation(value="delete a book from database by id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value="id") Long id)
            throws BookNotFoundException, ImpossibleDeleteBookException {
        try {
            bookService.deleteById( id );
            log.info( "the book " + id + " have been deleted" );
            return ResponseEntity
                    .status( HttpStatus.OK )
                    .body( "the book " + id + " have been deleted" );
        } catch (BookNotFoundException e) {
            log.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( e.getMessage() );
        } catch (ImpossibleDeleteBookException e) {
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


    @ApiOperation(value="Retrieve a book by its title, if it is registered in database")
    @GetMapping("/title")
    public ResponseEntity<?> findByTitle(@RequestParam String title)
            throws BookNotFoundException {
        try {
            BookDto bookDto=bookService.findByTitle( title );
            return new ResponseEntity<BookDto>( bookDto, HttpStatus.OK );
        } catch (BookNotFoundException e) {
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

}
