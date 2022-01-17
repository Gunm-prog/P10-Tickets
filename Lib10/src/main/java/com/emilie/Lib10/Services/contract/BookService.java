package com.emilie.Lib10.Services.contract;


import com.emilie.Lib10.Exceptions.BookAlreadyExistException;
import com.emilie.Lib10.Exceptions.BookNotFoundException;
import com.emilie.Lib10.Models.Dtos.BookDto;

import java.util.List;


public interface BookService {


    BookDto findById(Long id) throws BookNotFoundException;

    BookDto save(BookDto bookDto) throws BookAlreadyExistException;

    BookDto update(BookDto bookDto);

    boolean deleteById(Long id) throws BookNotFoundException;

    BookDto findByTitle(String title) throws BookNotFoundException;

    List<BookDto> findAll();

    List<BookDto> searchBooks(Long libraryId, String title, String isbn, String firstName, String lastName);


}
