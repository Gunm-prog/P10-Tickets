package com.emilie.Lib10.Services;

import com.emilie.Lib10.Exceptions.AuthorAlreadyExistException;
import com.emilie.Lib10.Exceptions.AuthorNotFoundException;
import com.emilie.Lib10.Exceptions.ImpossibleDeleteAuthorException;
import com.emilie.Lib10.Models.Dtos.AuthorDto;
import com.emilie.Lib10.Models.Dtos.BookDto;
import com.emilie.Lib10.Models.Entities.Author;
import com.emilie.Lib10.Models.Entities.Book;
import com.emilie.Lib10.Repositories.AuthorsRepository;
import com.emilie.Lib10.Services.impl.AuthorsServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorServiceImplUnitTest {

    AuthorsServiceImpl authorsServiceUnderTest;
    AuthorsRepository mockAuthorRepository = mock( AuthorsRepository.class );

    private AuthorDto getMockedAuthorDto(int id, boolean withBooks){
        AuthorDto authorDto = mock (AuthorDto.class);
        when( authorDto.getAuthorId() ).thenReturn( Long.valueOf( id ) );
        when( authorDto.getFirstName() ).thenReturn( "firstname" );
        when( authorDto.getLastName() ).thenReturn( "lastname" );
        HashSet<BookDto> books = new HashSet<>();
        if(withBooks) {
            for (int i = 0; i < 5; i++) {
                books.add(getMockedBookDto(i));
            }
        }
        when( authorDto.getBookDtos() ).thenReturn( books );
        return authorDto;
    }

    private Author getMockedAuthor(int id, boolean withBooks){
        Author author = mock (Author.class);
        when( author.getAuthorId() ).thenReturn( Long.valueOf( id ) );
        when( author.getFirstName() ).thenReturn( "firstname" );
        when( author.getLastName() ).thenReturn( "lastname" );
        HashSet<Book> books = new HashSet<>();
        if(withBooks){
            for(int i=0; i<5; i++){
                books.add( getMockedBook( i ) );
            }
        }
        when( author.getBooks() ).thenReturn( books );
        return author;
    }

    private Book getMockedBook(int id){
        Book book = mock(Book.class);
        when( book.getBookId() ).thenReturn( Long.valueOf( id ) );
        when( book.getTitle() ).thenReturn( "book title" );
        when( book.getIsbn() ).thenReturn( "book ISBN" );
        when( book.getSummary() ).thenReturn( "book SUMMARY" );
        return book;
    }

    private BookDto getMockedBookDto(int id){
        BookDto bookDto = mock(BookDto.class);
        when( bookDto.getBookId() ).thenReturn( Long.valueOf( id ) );
        when( bookDto.getTitle() ).thenReturn( "book title" );
        when( bookDto.getIsbn() ).thenReturn( "book ISBN" );
        when( bookDto.getSummary() ).thenReturn( "book SUMMARY" );
        return bookDto;
    }

    @Test
    void authorsFindAll_UT() {
        ArrayList<Author> authorsList = new ArrayList<>();
        when( mockAuthorRepository.findAll() ).thenReturn( authorsList );

        try{
            authorsServiceUnderTest = new AuthorsServiceImpl( mockAuthorRepository );

            assertThat( authorsServiceUnderTest.findAll() ).isInstanceOf( ArrayList.class );
            assertThat( authorsServiceUnderTest.findAll().size() ).isEqualTo( 0 );

            authorsList.add( getMockedAuthor(1, false) );
            authorsList.add( getMockedAuthor(2, false) );
            authorsList.add( getMockedAuthor(3, false) );

            assertThat( authorsServiceUnderTest.findAll() ).isInstanceOf( ArrayList.class );
            assertThat( authorsServiceUnderTest.findAll().size() ).isEqualTo( 3 );

        }catch(Exception e ){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void authorFindById_WhenNotFound_UT() {

        when( mockAuthorRepository.findById( any(Long.class) ) ).thenReturn( Optional.empty() );
        authorsServiceUnderTest = new AuthorsServiceImpl( mockAuthorRepository );

        try{
            authorsServiceUnderTest.findById( 9L );
            fail("AuthorNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( AuthorNotFoundException.class );
        }
    }

    @Test
    void authorFindById_Success_UD() {
        Author mockedAuthor = getMockedAuthor(1, true);
        when( mockAuthorRepository.findById( any(Long.class) ) ).thenReturn( Optional.of( mockedAuthor ) );
        authorsServiceUnderTest = new AuthorsServiceImpl( mockAuthorRepository );

        try{
            AuthorDto result = authorsServiceUnderTest.findById( any(Long.class) );
            assertThat( result ).isInstanceOf(AuthorDto.class);
            assertThat( result.getAuthorId() ).isEqualTo( mockedAuthor.getAuthorId() );
            assertThat( result.getFirstName() ).isEqualTo( mockedAuthor.getFirstName() );
            assertThat( result.getLastName() ).isEqualTo( mockedAuthor.getLastName() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void createAuthor_WhenAlreadyExist_UT() {
        Author mockedAuthor = getMockedAuthor(1, false);
        when( mockAuthorRepository.findByName( any(String.class), any(String.class) ) ).thenReturn( Optional.of( mockedAuthor ) );
        authorsServiceUnderTest = new AuthorsServiceImpl( mockAuthorRepository );

        try{
            authorsServiceUnderTest.save( getMockedAuthorDto(1, false) );
            fail("AuthorAlreadyExistException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(AuthorAlreadyExistException.class);
        }
    }

    @Test
    void createAuthor_whenSuccess() {
        Author mockedAuthor = getMockedAuthor(1, false);
        when( mockAuthorRepository.findByName( any(String.class), any(String.class) ) ).thenReturn( Optional.empty() );
        when( mockAuthorRepository.save( any(Author.class) )).thenReturn( mockedAuthor );
        authorsServiceUnderTest = new AuthorsServiceImpl( mockAuthorRepository );

        try{
            AuthorDto result = authorsServiceUnderTest.save( getMockedAuthorDto(1, false) );
            assertThat( result ).isInstanceOf( AuthorDto.class );
            assertThat( result.getAuthorId() ).isEqualTo( mockedAuthor.getAuthorId() );
            assertThat( result.getFirstName() ).isEqualTo( mockedAuthor.getFirstName() );
            assertThat( result.getLastName() ).isEqualTo( mockedAuthor.getLastName() );
        }catch( Exception e ){
            fail("Unexpected Exception: ", e);
        }

    }

    @Test
    void authorUpdate_whenNotFound_UT() {
        when( mockAuthorRepository.findById( any(Long.class) ) ).thenReturn( Optional.empty() );
        authorsServiceUnderTest = new AuthorsServiceImpl( mockAuthorRepository );

        try{
            authorsServiceUnderTest.update( getMockedAuthorDto(1, true) );
            fail("AuthorNotFoundException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(AuthorNotFoundException.class);
        }
    }

    @Test
    void authorUpdate_whenSuccess_UT() {
        Author mockedAuthor = getMockedAuthor(1, true);
        when( mockAuthorRepository.findById( any(Long.class) ) ).thenReturn( Optional.of( mockedAuthor ) );
        when( mockAuthorRepository.save( any(Author.class) )).thenReturn( mockedAuthor );
        authorsServiceUnderTest = new AuthorsServiceImpl( mockAuthorRepository );

        AuthorDto mockedAuthorDto = getMockedAuthorDto(1, true);
        try{
            AuthorDto result = authorsServiceUnderTest.update( mockedAuthorDto );
            assertThat(result).isInstanceOf( AuthorDto.class );
            assertThat( result.getAuthorId() ).isEqualTo( mockedAuthorDto.getAuthorId() );
            assertThat( result.getFirstName() ).isEqualTo( mockedAuthorDto.getFirstName() );
            assertThat( result.getLastName() ).isEqualTo( mockedAuthorDto.getLastName() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void authorDelete_whenNotFound_UT() {
        when( mockAuthorRepository.findById( any(Long.class) ) ).thenReturn( Optional.empty() );
        authorsServiceUnderTest = new AuthorsServiceImpl( mockAuthorRepository );

        try{
            authorsServiceUnderTest.deleteById( any(Long.class) );
            fail("AuthorNotFoundException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(AuthorNotFoundException.class);
        }
    }

    @Test
    void authorDelete_whenImpossibleDelete_CauseAuthorWithBooks_UT() {
        Author mockedAuthor = getMockedAuthor(1, true);
        when( mockAuthorRepository.findById( any(Long.class) ) ).thenReturn( Optional.of( mockedAuthor ) );
        authorsServiceUnderTest = new AuthorsServiceImpl( mockAuthorRepository );

        try{
            authorsServiceUnderTest.deleteById( any(Long.class) );
            fail("ImpossibleDeleteAuthorException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(ImpossibleDeleteAuthorException.class);
        }
    }

    @Test
    void authorDelete_whenSucess_UT() {
        Author mockedAuthor = getMockedAuthor(1, false);
        when( mockAuthorRepository.findById( any(Long.class) ) ).thenReturn( Optional.of( mockedAuthor ) );
        authorsServiceUnderTest = new AuthorsServiceImpl( mockAuthorRepository );

        try{
            authorsServiceUnderTest.deleteById( any(Long.class) );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }
}

