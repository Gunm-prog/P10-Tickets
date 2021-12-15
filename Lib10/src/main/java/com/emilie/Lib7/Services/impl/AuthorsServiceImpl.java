
package com.emilie.Lib7.Services.impl;

import com.emilie.Lib7.Exceptions.AuthorAlreadyExistException;
import com.emilie.Lib7.Exceptions.AuthorNotFoundException;
import com.emilie.Lib7.Exceptions.ImpossibleDeleteAuthorException;
import com.emilie.Lib7.Models.Dtos.AuthorDto;
import com.emilie.Lib7.Models.Dtos.BookDto;
import com.emilie.Lib7.Models.Dtos.CopyDto;
import com.emilie.Lib7.Models.Entities.Author;
import com.emilie.Lib7.Models.Entities.Book;
import com.emilie.Lib7.Models.Entities.Copy;
import com.emilie.Lib7.Repositories.AuthorsRepository;
import com.emilie.Lib7.Services.contract.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthorsServiceImpl implements AuthorsService {


    private final AuthorsRepository authorsRepository;


    @Autowired
    public AuthorsServiceImpl(AuthorsRepository authorsRepository) {

        this.authorsRepository=authorsRepository;

    }

    @Override
    public List<AuthorDto> findAll() {
        List<Author> authors=authorsRepository.findAll();
        List<AuthorDto> authorDtos=new ArrayList<>();
        for (Author author : authors) {
            AuthorDto authorDto=authorToAuthorDto( author );
            authorDtos.add( authorDto );
        }
        return authorDtos;
    }

    @Override
    public AuthorDto findById(Long id) throws AuthorNotFoundException {
        Optional<Author> optionalAuthor=authorsRepository.findById( id );
        if (!optionalAuthor.isPresent()) {
            throw new AuthorNotFoundException( "Author not found" );
        }
        Author author=optionalAuthor.get();
        return authorToAuthorDto( author );
    }


    @Override
    public AuthorDto save(AuthorDto authorDto)
            throws AuthorAlreadyExistException {
        Optional<Author> optionalAuthor=authorsRepository.findByName( authorDto.getFirstName(), authorDto.getLastName() );
        if (optionalAuthor.isPresent()) {
            throw new AuthorAlreadyExistException( "author already exists" );
        }
        Author author=authorDtoToAuthor( authorDto );
        author=authorsRepository.save( author );

        return authorToAuthorDto( author );
    }


    @Override
    public AuthorDto update(AuthorDto authorDto)
            throws AuthorNotFoundException {
        Optional<Author> optionalAuthor=authorsRepository.findById( authorDto.getAuthorId() );
        if (!optionalAuthor.isPresent()) {
            throw new AuthorNotFoundException( "author not found" );
        }
        Author author=optionalAuthor.get();
        author.setFirstName( authorDto.getFirstName() );
        author.setLastName( authorDto.getLastName() );
        author=authorsRepository.save( author );
        return authorToAuthorDto( author );
    }

    @Override
    public boolean deleteById(Long id)
            throws AuthorNotFoundException, ImpossibleDeleteAuthorException {
        Optional<Author> optionalAuthor=authorsRepository.findById( id );
        if (!optionalAuthor.isPresent()) {
            throw new AuthorNotFoundException( "author not found" );
        } else if (optionalAuthor.get().getBooks().size() > 0) {
            throw new ImpossibleDeleteAuthorException( "This author " + id + " have existing books" );
        }
        try {
            authorsRepository.deleteById( id );
        } catch (Exception e) {
            return false;
        }
        return true;
    }



    private AuthorDto authorToAuthorDto(Author author) {
        AuthorDto authorDto=new AuthorDto();
        authorDto.setAuthorId( author.getAuthorId() );
        authorDto.setFirstName( author.getFirstName() );
        authorDto.setLastName( author.getLastName() );

        Set<BookDto> bookDtos=new HashSet<>();
        if (author.getBooks() != null) {
            for (Book book : author.getBooks()) {
                BookDto bookDto=new BookDto();
                bookDto.setBookId( book.getBookId() );
                bookDto.setTitle( book.getTitle() );
                bookDto.setIsbn( book.getIsbn() );
                bookDto.setSummary( book.getSummary() );

                Set<CopyDto> copyDtos=new HashSet<>();
                bookDto.setCopyDtos( copyDtos );
                bookDtos.add( bookDto );
            }
        }
        authorDto.setBookDtos( bookDtos );


        return authorDto;
    }


    private Author authorDtoToAuthor(AuthorDto authorDto) {
        Author author=new Author();
        author.setAuthorId( authorDto.getAuthorId() );
        author.setFirstName( authorDto.getFirstName() );
        author.setLastName( authorDto.getLastName() );


        Set<Book> books=new HashSet<>();
        if (authorDto.getBookDtos() != null) {
            for (BookDto bookDto : authorDto.getBookDtos()) {
                Book book=new Book();
                book.setBookId( bookDto.getBookId() );
                book.setTitle( bookDto.getTitle() );
                book.setIsbn( bookDto.getIsbn() );
                book.setSummary( bookDto.getSummary() );

                Set<Copy> copies=new HashSet<>();
                book.setCopies( copies );

                books.add( book );
            }
        }

        author.setBooks( books );

        return author;
    }


}
