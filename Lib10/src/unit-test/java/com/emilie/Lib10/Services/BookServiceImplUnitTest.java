package com.emilie.Lib10.Services;

import com.emilie.Lib10.Exceptions.*;
import com.emilie.Lib10.Models.Dtos.*;
import com.emilie.Lib10.Models.Entities.*;
import com.emilie.Lib10.Repositories.AuthorsRepository;
import com.emilie.Lib10.Repositories.BookRepository;
import com.emilie.Lib10.Services.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookServiceImplUnitTest {

    BookServiceImpl bookServiceUnderTest;
    BookRepository mockBookRepository = mock( BookRepository.class );
    AuthorsRepository mockAuthorRepository = mock( AuthorsRepository.class );

    private BookDto getMockedBookDto(int id, boolean withCopies){
        AuthorDto mockedAuthorDto = getMockedAuthorDto();
        BookDto bookDto = mock (BookDto.class );
        when( bookDto.getBookId() ).thenReturn( Long.valueOf( id ) );
        when( bookDto.getIsbn() ).thenReturn( "book ISBN ");
        when( bookDto.getTitle() ).thenReturn( "book TITLE ");
        when( bookDto.getSummary() ).thenReturn( "book SUMMARY ");

        when( bookDto.getAuthorDto() ).thenReturn( mockedAuthorDto );

   //     HashSet<CopyDto> copies = new HashSet<>();
      /*  if(withCopies) {
            for (int i = 0; i < 5; i++) {
                copies.add(getMockedCopyDto(i));
            }
        }*/
   //     when( bookDto.getCopyDtos() ).thenReturn( copies );
        //todo other params ?
        return bookDto;
    }

    private Book getMockedBook(int id, boolean withCopies){
        Author mockedAuthor = getMockedAuthor();
        Book book = mock (Book.class );
        when( book.getBookId() ).thenReturn( Long.valueOf( id ) );
        when( book.getIsbn() ).thenReturn( "book ISBN " );
        when( book.getTitle() ).thenReturn( "book TITLE ");
        when( book.getSummary() ).thenReturn( "book SUMMARY ");

        when( book.getAuthor() ).thenReturn( mockedAuthor );

        //todo finish book mock before mockCopies...
        HashSet<Copy> copies = new HashSet<>();
        if(withCopies) {
            for (int i = 0; i < 5; i++) {
                copies.add(getMockedCopy(i));
            }
        }
        when( book.getCopies() ).thenReturn( copies );

        //todo other params ?
        return book;
    }

    private CopyDto getMockedCopyDto(int id){
        LibraryDto libraryDto = getMockedLibraryDto();

        CopyDto copyDto = mock ( CopyDto.class );
        when( copyDto.getId() ).thenReturn( Long.valueOf( id ));
        when( copyDto.getLibraryDto() ).thenReturn( libraryDto );
        return copyDto;
    }

    private Copy getMockedCopy(int id){
        Library library = getMockedLibrary();

        Copy copy = mock ( Copy.class );
        when( copy.getId() ).thenReturn( Long.valueOf( id ));
        when( copy.getLibrary() ).thenReturn( library );
        return copy;
    }

    private LibraryDto getMockedLibraryDto(){
        AddressDto mockedAddressDto = getMockedAddressDto();

        LibraryDto libraryDto = mock (LibraryDto.class );
        when( libraryDto.getLibraryId() ).thenReturn( 1L );
        when( libraryDto.getAddressDto() ).thenReturn( mockedAddressDto );
        return libraryDto;
    }

    private Library getMockedLibrary(){
        Address mockedAddress = getMockedAddress();

        Library library = mock (Library.class );
        when( library.getLibraryId() ).thenReturn( 1L );
        when( library.getAddress() ).thenReturn( mockedAddress );
        return library;
    }

    private Address getMockedAddress(){
        Address address = mock (Address.class);
        when( address.getCity() ).thenReturn( "city" );
        when( address.getStreet() ).thenReturn( "street" );
        when( address.getZipCode() ).thenReturn( "zipCode" );
        when( address.getNumber() ).thenReturn( 99 );
        return address;
    }

    private AddressDto getMockedAddressDto(){
        AddressDto addressDto = mock (AddressDto.class);
        when( addressDto.getCity() ).thenReturn( "city" );
        when( addressDto.getStreet() ).thenReturn( "street" );
        when( addressDto.getZipCode() ).thenReturn( "zipCode" );
        when( addressDto.getNumber() ).thenReturn( 99 );
        return addressDto;
    }

    private AuthorDto getMockedAuthorDto(){
        AuthorDto authorDto = mock (AuthorDto.class);
        when( authorDto.getAuthorId() ).thenReturn( 1L );
       /* when( authorDto.getFirstName() ).thenReturn( "author firstname");
        when( authorDto.getLastName() ).thenReturn( "author lastname");*/
        return authorDto;
    }

    private Author getMockedAuthor(){
        Author author = mock (Author.class);
        when( author.getAuthorId() ).thenReturn( 1L );
     /*   when( author.getFirstName() ).thenReturn( "author firstname");
        when( author.getLastName() ).thenReturn( "author lastname");*/
        return author;
    }

    @Test
    void book_findAll_UT() {
        ArrayList<Book> bookList = new ArrayList<>();
        when( mockBookRepository.findAll() ).thenReturn( bookList );
        try{
            bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository);

            assertThat( bookServiceUnderTest.findAll() ).isInstanceOf( ArrayList.class );
            assertThat( bookServiceUnderTest.findAll().size() ).isEqualTo( 0 );

            bookList.add( getMockedBook( 1, true ));
            bookList.add( getMockedBook( 2, true ));
            bookList.add( getMockedBook( 3, true ));

            assertThat( bookServiceUnderTest.findAll() ).isInstanceOf( ArrayList.class );
            assertThat( bookServiceUnderTest.findAll().size() ).isEqualTo( 3 );

        }catch(Exception e ){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void book_search_UT() {
        try{
            //// testing with empty listResult
            ArrayList<Book> mockList = new ArrayList<>();
            when( mockBookRepository.searchBooks(
                    any(String.class), any(String.class), any(String.class), any(String.class) )
            ).thenReturn( mockList );
            when( mockBookRepository.searchBooksByLibrary(
                    any(Long.class), any(String.class), any(String.class), any(String.class), any(String.class) )
            ).thenReturn( mockList );
            bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository);

            //libraryId less
            List<BookDto> resultList1 = bookServiceUnderTest.searchBooks( null, any(String.class), any(String.class), any(String.class), any(String.class));
            assertThat( resultList1 ).isInstanceOf( ArrayList.class );
            assertThat( resultList1.size() ).isEqualTo( 0 );

            //libraryId with
            List<BookDto> resultList2 = bookServiceUnderTest.searchBooks( 1L, "ojnojon", "ojnojon", "ojnojon", "ojnojon");
            assertThat( resultList2 ).isInstanceOf( ArrayList.class );
            assertThat( resultList2.size() ).isEqualTo( 0 );

            //// testing with not null listResult
            ArrayList<Book> bookList = new ArrayList<>();
            bookList.add( getMockedBook( 1, true ));
            bookList.add( getMockedBook( 2, true ));
            bookList.add( getMockedBook( 3, true ));
            when( mockBookRepository.searchBooks(
                    any(String.class), any(String.class), any(String.class), any(String.class) )
            ).thenReturn( bookList );
            when( mockBookRepository.searchBooksByLibrary(
                    any(Long.class), any(String.class), any(String.class), any(String.class), any(String.class) )
            ).thenReturn( bookList );

            //libraryId less
            List<BookDto> resultList3 = bookServiceUnderTest.searchBooks(   null, "ojnojon", "ojnojon", "ojnojon", "ojnojon" );
            assertThat( resultList3 ).isInstanceOf( ArrayList.class );
            assertThat( resultList3.size() ).isEqualTo( 3 );
            assertThat( resultList3.get(1).getCopyDtos().size() ).isEqualTo( 5 );

            //libraryId with
            List<BookDto> resultList4 = bookServiceUnderTest.searchBooks(   1L, "ojnojon", "ojnojon", "ojnojon", "ojnojon" );
            assertThat( resultList4 ).isInstanceOf( ArrayList.class );
            assertThat( resultList4.size() ).isEqualTo( 3 );
            assertThat( resultList4.get(1).getCopyDtos().size() ).isEqualTo( 5 );

        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }

    }

    @Test
    void book_findById_WhenNotFound_UT() {
        when( mockBookRepository.findById( any(Long.class) ) ).thenReturn( Optional.empty() );
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        try{
            bookServiceUnderTest.findById( 9L );
            fail("BookNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( BookNotFoundException.class );
        }
    }

    @Test
    void book_FindById_WhenSuccess_UT() {
        Book mockedBook = getMockedBook(1, true);
        when( mockBookRepository.findById( any(Long.class) ) ).thenReturn( Optional.of( mockedBook ) );
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        try{
            BookDto result = bookServiceUnderTest.findById( any(Long.class) );
            assertThat( result ).isInstanceOf( BookDto.class );
            assertThat( result.getBookId() ).isEqualTo( mockedBook.getBookId() );
            assertThat( result.getTitle() ).isEqualTo( mockedBook.getTitle() );
            assertThat( result.getIsbn() ).isEqualTo( mockedBook.getIsbn() );
            assertThat( result.getSummary() ).isEqualTo( mockedBook.getSummary() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void book_create_WhenAlreadyExist_UT() {
        Book mockedBook = getMockedBook(1, false);
        when( mockBookRepository.findByTitle( any(String.class) ) ).thenReturn( Optional.of( mockedBook ) );
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        try{
            bookServiceUnderTest.save( getMockedBookDto(1, false) );
            fail("BookAlreadyExistException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(BookAlreadyExistException.class);
        }
    }

    @Test
    void book_create_WhenAuthorRequired_UT() {
        when( mockBookRepository.findByTitle( any(String.class) ) ).thenReturn( Optional.empty() );
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        BookDto mockedBookDto = getMockedBookDto(1, false);
        when( mockedBookDto.getAuthorDto() ).thenReturn( null );
        try{
            bookServiceUnderTest.save( mockedBookDto );
            fail("AuthorNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(AuthorNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("author is required");
        }
    }

    @Test
    void book_create_WhenAuthorNotFound_UT() {
        when( mockBookRepository.findByTitle( any(String.class) ) ).thenReturn( Optional.empty() );
        when( mockAuthorRepository.findById( any(Long.class ) ) ).thenReturn( Optional.empty() );
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        BookDto mockedBookDto = getMockedBookDto(1, false);
        try{
            bookServiceUnderTest.save( mockedBookDto );
            fail("AuthorNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(AuthorNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("author " + mockedBookDto.getAuthorDto().getAuthorId() + " not found");
        }
    }

    @Test
    void book_create_WhenSuccess_UT() {
        Book mockedBook = getMockedBook(1, false);
        when( mockBookRepository.findByTitle( any(String.class) ) ).thenReturn( Optional.empty() );
        when( mockAuthorRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of( mock (Author.class) ) );
        when( mockBookRepository.save( any(Book.class) )).thenReturn( mockedBook );
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        BookDto mockedBookDto = getMockedBookDto(1, false);
        try{
            BookDto result = bookServiceUnderTest.save( mockedBookDto );
            assertThat( result ).isInstanceOf( BookDto.class );
            assertThat( result.getBookId() ).isEqualTo( mockedBook.getBookId() );
            assertThat( result.getTitle() ).isEqualTo( mockedBook.getTitle() );
            assertThat( result.getIsbn() ).isEqualTo( mockedBook.getIsbn() );
            assertThat( result.getSummary() ).isEqualTo( mockedBook.getSummary() );
            assertThat( result.getAuthorDto() ).isInstanceOf( AuthorDto.class );
            assertThat( result.getAuthorDto().getAuthorId() ).isEqualTo( mockedBook.getAuthor().getAuthorId() );
        }catch( Exception e ){
            fail("Unexpected Exception: ", e);
        }

    }

    @Test
    void book_Update_WhenBookNotFound_UT() {
        when( mockBookRepository.findById( any(Long.class) ) ).thenReturn( Optional.empty() );
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        BookDto mockedBookDto = getMockedBookDto(1, false);
        try{
            bookServiceUnderTest.update( mockedBookDto );
            fail("BookNotFoundException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(BookNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "book " + mockedBookDto.getBookId() + " not found" );
        }
    }

    @Test
    void book_Update_WhenSuccess_UT() {
        Book mockedBook = getMockedBook(1, false);
        when( mockBookRepository.findById( any(Long.class) ) ).thenReturn( Optional.of( mockedBook ) );
        when( mockBookRepository.save( any(Book.class) )).thenReturn( mockedBook );
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        BookDto mockedBookDto = getMockedBookDto(1, false);
        try{
            BookDto result = bookServiceUnderTest.update( mockedBookDto );
            assertThat( result ).isInstanceOf( BookDto.class );
            assertThat( result.getBookId() ).isEqualTo( mockedBook.getBookId() );
            assertThat( result.getTitle() ).isEqualTo( mockedBook.getTitle() );
            assertThat( result.getIsbn() ).isEqualTo( mockedBook.getIsbn() );
            assertThat( result.getSummary() ).isEqualTo( mockedBook.getSummary() );
            assertThat( result.getAuthorDto() ).isInstanceOf( AuthorDto.class );
            assertThat( result.getAuthorDto().getAuthorId() ).isEqualTo( mockedBook.getAuthor().getAuthorId() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void book_delete_WhenBookNotFound_UT() {
        when( mockBookRepository.findById( any(Long.class) ) ).thenReturn( Optional.empty() );
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        BookDto mockedBookDto = getMockedBookDto(1, false);
        try{
            bookServiceUnderTest.deleteById( mockedBookDto.getBookId() );
            fail("BookNotFoundException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(BookNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "book " + mockedBookDto.getBookId() + " not found" );
        }
    }

    @Test
    void book_delete_WhenBookHaveReservation_UT() {
        Book mockedBook = getMockedBook(1, false);
            ArrayList<Reservation> rsvList = new ArrayList<>();
                rsvList.add( mock( Reservation.class ) );
        when( mockedBook.getReservationList() ).thenReturn( rsvList );

        when( mockBookRepository.findById( any(Long.class) ) ).thenReturn( Optional.of( mockedBook ) );
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        BookDto mockedBookDto = getMockedBookDto(1, false);
        try{
            bookServiceUnderTest.deleteById( mockedBookDto.getBookId() );
            fail("ImpossibleDeleteBookException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(ImpossibleDeleteBookException.class);
            assertThat( e.getMessage() ).isEqualTo( "This book " + mockedBook.getBookId() + " have existing reservation" );
        }
    }

    @Test
    void book_delete_WhenBookHaveCopy_UT() {
        Book mockedBook = getMockedBook(1, false);
            HashSet<Copy> copies = new HashSet<>();
                copies.add( mock( Copy.class ) );
        when( mockedBook.getCopies() ).thenReturn( copies );

        when( mockBookRepository.findById( any(Long.class) ) ).thenReturn( Optional.of( mockedBook ) );
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        BookDto mockedBookDto = getMockedBookDto(1, false);
        try{
            bookServiceUnderTest.deleteById( mockedBookDto.getBookId() );
            fail("ImpossibleDeleteBookException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(ImpossibleDeleteBookException.class);
            assertThat( e.getMessage() ).isEqualTo( "This book " + mockedBook.getBookId() + " have existing copy" );
        }
    }

    @Test
    void book_delete_WhenSuccess_UT() {
        Book mockedBook = getMockedBook(1, false);
        when( mockBookRepository.findById( any(Long.class) ) ).thenReturn( Optional.of( mockedBook ) );
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        try{
            bookServiceUnderTest.deleteById( any(Long.class) );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void book_findByTitle_WhenNotFound_UT() {
        when( mockBookRepository.findByTitle( any(String.class) ) ).thenReturn( Optional.empty() );
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        try{
            bookServiceUnderTest.findByTitle( "asked booktitle ");
            fail("BookNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( BookNotFoundException.class );
        }
    }

    @Test
    void book_findByTitle_WhenSuccess_UT() {
        Book mockedBook = getMockedBook(1, true);
        when( mockBookRepository.findByTitle( any(String.class) )  ).thenReturn( Optional.of( mockedBook ) );
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        try{
            BookDto result = bookServiceUnderTest.findByTitle( "asked book Title" );
            assertThat( result ).isInstanceOf( BookDto.class );
            assertThat( result.getBookId() ).isEqualTo( mockedBook.getBookId() );
            assertThat( result.getTitle() ).isEqualTo( mockedBook.getTitle() );
            assertThat( result.getIsbn() ).isEqualTo( mockedBook.getIsbn() );
            assertThat( result.getSummary() ).isEqualTo( mockedBook.getSummary() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void book_isAvailable_UT() {
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        Copy copy1Mocked = mock( Copy.class );
            when( copy1Mocked.isAvailable() ).thenReturn( false );

        Book mockedBook = getMockedBook(1, false);
            HashSet<Copy> copies = new HashSet<>();
            copies.add( copy1Mocked );
        when( mockedBook.getCopies() ).thenReturn( copies );

        assertThat( bookServiceUnderTest.bookIsAvailable( mockedBook ) ).isInstanceOf( Boolean.class );
        assertThat( bookServiceUnderTest.bookIsAvailable( mockedBook ) ).isEqualTo( false );

        Copy copy2Mocked = mock( Copy.class );
        when( copy2Mocked.isAvailable() ).thenReturn( true );

        copies.add( copy2Mocked );

        when( mockedBook.getCopies() ).thenReturn( copies );

        assertThat( bookServiceUnderTest.bookIsAvailable( mockedBook ) ).isInstanceOf( Boolean.class );
        assertThat( bookServiceUnderTest.bookIsAvailable( mockedBook ) ).isEqualTo( true );

    }

    @Test
    void book_isNewBookValid_whenRequiredAuthorException_UT() {
        BookDto mockedBookDto = getMockedBookDto( 1, false);
        when( mockedBookDto.getBookId() ).thenReturn( null);
        when( mockedBookDto.getAuthorDto() ).thenReturn( null );

        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        try{
            bookServiceUnderTest.isNewBookValid( mockedBookDto );
            fail("AuthorNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( AuthorNotFoundException.class );
            assertThat( e.getMessage() ).isEqualTo( "author param is required" );
        }

    }

    @Test
    void book_isNewBookValid_whenAuthorNotFoundException_UT() {
        BookDto mockedBookDto = getMockedBookDto( 1, false);
        when( mockedBookDto.getBookId() ).thenReturn( null);

        when( mockAuthorRepository.findById( any(Long.class) ) ).thenReturn( Optional.empty() );
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        try{
            bookServiceUnderTest.isNewBookValid( mockedBookDto );
            fail("AuthorNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( AuthorNotFoundException.class );
            assertThat( e.getMessage() ).isEqualTo( "author " + mockedBookDto.getAuthorDto().getAuthorId() + " not found" );
        }

    }

    @Test
    void book_isNewBookValid_whenBookAlreadyExistException_UT() {
        BookDto mockedBookDto = getMockedBookDto( 1, false);
        when( mockedBookDto.getBookId() ).thenReturn( null);

        Book mockedBook = getMockedBook(1,false);

        when( mockBookRepository.findByTitle( any(String.class) ) ).thenReturn( Optional.of( mockedBook ) );
        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        try{
            bookServiceUnderTest.isNewBookValid( mockedBookDto );
            fail("BookAlreadyExistException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( BookAlreadyExistException.class );
            assertThat( e.getMessage() ).isEqualTo( "book " + mockedBookDto.getTitle() + " already exists" );
        }

    }

    @Test
    void book_getmaxReservationForBook_UT() {
        Book mockedBook = getMockedBook(1,true);

        bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );

        try{
            assertThat( bookServiceUnderTest.getMaxReservationForBook( mockedBook ) ).isInstanceOf( Integer.class );
            assertThat( bookServiceUnderTest.getMaxReservationForBook( mockedBook ) ).isEqualTo( 10 );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }

    }

    @Test
    void book_getUserPositionInReservationsList_UT() {
        try{

            Book mockedBook = getMockedBook(1,true);

                Reservation mockedUserReservation = mock(Reservation.class);
                when( mockedUserReservation.getId() ).thenReturn( 9L );

                ArrayList<Reservation> rsvList = new ArrayList<>();
                    Reservation otherReservation1 = mock(Reservation.class);
                    when( otherReservation1.getId() ).thenReturn( 7L );
                rsvList.add( otherReservation1 );
                rsvList.add (mockedUserReservation );
                    Reservation otherReservation2 = mock(Reservation.class);
                    when( otherReservation1.getId() ).thenReturn( 10L );
                rsvList.add( otherReservation2);

            when( mockedBook.getReservationList() ).thenReturn( rsvList );

            when( mockedUserReservation.getBook() ).thenReturn( mockedBook );

            bookServiceUnderTest = new BookServiceImpl( mockBookRepository, mockAuthorRepository );
            assertThat( bookServiceUnderTest.getUserPosition( mockedUserReservation ) ).isInstanceOf( Integer.class );
            assertThat( bookServiceUnderTest.getUserPosition( mockedUserReservation ) ).isEqualTo( 2 );

        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }

    }
}
