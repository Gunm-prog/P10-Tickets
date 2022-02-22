package com.emilie.Lib10.Services;

import com.emilie.Lib10.Exceptions.*;
import com.emilie.Lib10.Models.Dtos.*;
import com.emilie.Lib10.Models.Entities.*;
import com.emilie.Lib10.Repositories.BookRepository;
import com.emilie.Lib10.Repositories.CopyRepository;
import com.emilie.Lib10.Repositories.LibraryRepository;
import com.emilie.Lib10.Services.impl.CopyServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CopyServiceImplUnitTest {

    CopyServiceImpl copyServiceUnderTest;
    CopyRepository mockedCopyRepository = mock (CopyRepository.class );
    BookRepository mockedBookRepository = mock (BookRepository.class );
    LibraryRepository mockedLibraryRepository = mock (LibraryRepository.class );

    private CopyDto getMockedCopyDto(int id,  boolean availability){
        BookDto bookDto = getMockedBookDto( 1);
        LibraryDto libraryDto = getMockedLibraryDto();

        CopyDto copyDto = mock ( CopyDto.class );
        when( copyDto.getId() ).thenReturn( Long.valueOf( id ));
        when( copyDto.isAvailable() ).thenReturn( availability );
        when( copyDto.getBookDto() ).thenReturn( bookDto );
        when( copyDto.getLibraryDto() ).thenReturn( libraryDto );
        return copyDto;
    }

    private Copy getMockedCopy(int id, boolean availability){
        Book book = getMockedBook( 1 );
        Library library = getMockedLibrary();

        Copy copy = mock ( Copy.class );
        when( copy.getId() ).thenReturn( Long.valueOf( id ));
        when( copy.isAvailable() ).thenReturn( availability );
        when( copy.getBook() ).thenReturn( book );
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

    private BookDto getMockedBookDto(int id){
        AuthorDto mockedAuthorDto = getMockedAuthorDto();

        BookDto bookDto = mock (BookDto.class );
        when( bookDto.getBookId() ).thenReturn( Long.valueOf( id ) );
        when( bookDto.getIsbn() ).thenReturn( "book ISBN ");
        when( bookDto.getTitle() ).thenReturn( "book TITLE ");
        when( bookDto.getSummary() ).thenReturn( "book SUMMARY ");

        when( bookDto.getAuthorDto() ).thenReturn( mockedAuthorDto );

        return bookDto;
    }

    private Book getMockedBook(int id ){
        Author mockedAuthor = getMockedAuthor();

        Book book = mock (Book.class );
        when( book.getBookId() ).thenReturn( Long.valueOf( id ) );
        when( book.getIsbn() ).thenReturn( "book ISBN " );
        when( book.getTitle() ).thenReturn( "book TITLE ");
        when( book.getSummary() ).thenReturn( "book SUMMARY ");

        when( book.getAuthor() ).thenReturn( mockedAuthor );

        return book;
    }

    private AuthorDto getMockedAuthorDto(){
        AuthorDto authorDto = mock (AuthorDto.class);
        when( authorDto.getAuthorId() ).thenReturn( 1L );
        when( authorDto.getFirstName() ).thenReturn( "author firstname");
        when( authorDto.getLastName() ).thenReturn( "author lastname");
        return authorDto;
    }

    private Author getMockedAuthor(){
        Author author = mock (Author.class);
        when( author.getAuthorId() ).thenReturn( 1L );
        when( author.getFirstName() ).thenReturn( "author firstname");
        when( author.getLastName() ).thenReturn( "author lastname");
        return author;
    }

    @Test
    void copy_findAll_UT() {
        try{
            copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);
            assertThat( copyServiceUnderTest.findAll() ).isNull();
        }catch(Exception e ){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void copy_searchCopies_UT() {
        try{
            //// testing with empty listResult
            List<Copy> emptyResult = new ArrayList<>();
            when( mockedCopyRepository.searchCopies(  any(String.class), any(String.class), any(String.class), any(String.class) ) ).thenReturn( emptyResult );
            when( mockedCopyRepository.searchCopiesByLibrary( any(Long.class), any(String.class), any(String.class), any(String.class), any(String.class) ) ).thenReturn( emptyResult );
            copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

            //libraryId less
            List<CopyDto> resultList1 = copyServiceUnderTest.searchCopies( null, any(String.class), any(String.class), any(String.class), any(String.class));
            assertThat( resultList1 ).isInstanceOf( ArrayList.class );
            assertThat( resultList1.size() ).isEqualTo( 0 );

            //libraryId with
            List<CopyDto> resultList2 = copyServiceUnderTest.searchCopies( 1L, "ojnojon", "ojnojon", "ojnojon", "ojnojon");
            assertThat( resultList2 ).isInstanceOf( ArrayList.class );
            assertThat( resultList2.size() ).isEqualTo( 0 );

            //// testing with not null listResult
            ArrayList<Copy> copiesList = new ArrayList<>();
            copiesList.add( getMockedCopy( 1, true));
            copiesList.add( getMockedCopy( 2,true));
            copiesList.add( getMockedCopy( 3, false));
            when( mockedCopyRepository.searchCopies(  any(String.class), any(String.class), any(String.class), any(String.class) ) ).thenReturn( copiesList );
            when( mockedCopyRepository.searchCopiesByLibrary( any(Long.class), any(String.class), any(String.class), any(String.class), any(String.class) ) ).thenReturn( copiesList );

            //libraryId less
            List<CopyDto> resultList3 = copyServiceUnderTest.searchCopies( null, "ojnojon", "ojnojon", "ojnojon", "ojnojon");
            assertThat( resultList3 ).isInstanceOf( ArrayList.class );
            assertThat( resultList3.size() ).isEqualTo( 3 );

            //libraryId with
            List<CopyDto> resultList4 = copyServiceUnderTest.searchCopies( 1L, "ojnojon", "ojnojon", "ojnojon", "ojnojon");
            assertThat( resultList4 ).isInstanceOf( ArrayList.class );
            assertThat( resultList4.size() ).isEqualTo( 3 );

        }catch(Exception e ){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void copy_findById_WhenNotFound_UT() {
        when( mockedCopyRepository.findById( any(Long.class) ) ).thenReturn( Optional.empty() );

        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        try{
            copyServiceUnderTest.findById( 9L );
            fail("CopyNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( CopyNotFoundException.class );
        }
    }

    @Test
    void copy_FindById_WhenSuccess_UT() {
        Copy mockedCopy = getMockedCopy(1 , true);
        when( mockedCopyRepository.findById( any(Long.class) ) ).thenReturn( Optional.of( mockedCopy ) );

        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        try{
            CopyDto result = copyServiceUnderTest.findById( any(Long.class) );
            assertThat( result ).isInstanceOf( CopyDto.class );
            assertThat( result.getId() ).isEqualTo( mockedCopy.getId() );
            assertThat( result.isAvailable() ).isEqualTo( mockedCopy.isAvailable() );
            assertThat( result.getBookDto().getTitle() ).isEqualTo( mockedCopy.getBook().getTitle() );
            assertThat( result.getBookDto().getSummary() ).isEqualTo( mockedCopy.getBook().getSummary() );
            assertThat( result.getBookDto().getIsbn() ).isEqualTo( mockedCopy.getBook().getIsbn() );
            assertThat( result.getBookDto().getAuthorDto().getAuthorId() ).isEqualTo( mockedCopy.getBook().getAuthor().getAuthorId() );
            assertThat( result.getBookDto().getAuthorDto().getLastName() ).isEqualTo( mockedCopy.getBook().getAuthor().getLastName() );
            assertThat( result.getBookDto().getAuthorDto().getFirstName() ).isEqualTo( mockedCopy.getBook().getAuthor().getFirstName() );
            assertThat( result.getLibraryDto().getLibraryId() ).isEqualTo( mockedCopy.getLibrary().getLibraryId() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void copy_create_WhenBookRequired_UT() {

        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        CopyDto mockedCopyDto = getMockedCopyDto(1, false);
        when( mockedCopyDto.getBookDto() ).thenReturn( null );
        try{
            copyServiceUnderTest.save( mockedCopyDto );
            fail("BookNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(BookNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("book param is required");
        }
    }

    @Test
    void copy_create_WhenLibraryRequired_UT() {

        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        CopyDto mockedCopyDto = getMockedCopyDto(1, false);
        when( mockedCopyDto.getLibraryDto() ).thenReturn( null );
        try{
            copyServiceUnderTest.save( mockedCopyDto );
            fail("LibraryNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(LibraryNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("library param is required");
        }
    }

    @Test
    void copy_create_WhenBookNotFound_UT() {
        CopyDto mockedCopyDto = getMockedCopyDto(1, false);

        when( mockedBookRepository.findById( any(Long.class ) ) ).thenReturn( Optional.empty() );
        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        try{
            copyServiceUnderTest.save( mockedCopyDto );
            fail("BookNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(BookNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("book " + mockedCopyDto.getBookDto().getBookId() + " not found");
        }
    }

    @Test
    void copy_create_WhenLibraryNotFound_UT() {
        CopyDto mockedCopyDto = getMockedCopyDto(1, false);
        Book mockedBook =  getMockedBook( 1 );

        when( mockedBookRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of( mockedBook ) );
        when( mockedLibraryRepository.findById( any(Long.class ) ) ).thenReturn( Optional.empty() );
        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        try{
            copyServiceUnderTest.save( mockedCopyDto );
            fail("LibraryNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(LibraryNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("library " + mockedCopyDto.getLibraryDto().getLibraryId() + " not found");
        }
    }
    @Test
    void copy_create_WhenSuccess_UT() {
        CopyDto mockedCopyDto = getMockedCopyDto(1, false);
        Book mockedBook = getMockedBook(1);
        Library mockedLibrary = getMockedLibrary();
        Copy mockedCopy = getMockedCopy(1, true);

        when( mockedBookRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of( mockedBook ) );
        when( mockedLibraryRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of( mockedLibrary ) );
        when( mockedCopyRepository.save( any(Copy.class) ) ).thenReturn( mockedCopy );
        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);


        try{
            CopyDto result = copyServiceUnderTest.save( mockedCopyDto );
            assertThat( result ).isInstanceOf( CopyDto.class );
            assertThat( result.getId() ).isEqualTo( mockedCopyDto.getId() );
            assertThat( result.isAvailable() ).isEqualTo( true );
            assertThat( result.getBookDto().getTitle() ).isEqualTo( mockedCopyDto.getBookDto().getTitle() );
            assertThat( result.getBookDto().getSummary() ).isEqualTo( mockedCopyDto.getBookDto().getSummary() );
            assertThat( result.getBookDto().getIsbn() ).isEqualTo( mockedCopyDto.getBookDto().getIsbn() );
            assertThat( result.getBookDto().getAuthorDto().getAuthorId() ).isEqualTo( mockedCopyDto.getBookDto().getAuthorDto().getAuthorId() );
            assertThat( result.getBookDto().getAuthorDto().getLastName() ).isEqualTo( mockedCopyDto.getBookDto().getAuthorDto().getLastName() );
            assertThat( result.getBookDto().getAuthorDto().getFirstName() ).isEqualTo( mockedCopyDto.getBookDto().getAuthorDto().getFirstName() );
            assertThat( result.getLibraryDto().getLibraryId() ).isEqualTo( mockedCopyDto.getLibraryDto().getLibraryId() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void copy_Update_WhenBookRequired_UT() {
        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        CopyDto mockedCopyDto = getMockedCopyDto(1, false);
        when( mockedCopyDto.getBookDto() ).thenReturn( null );

        try{
            copyServiceUnderTest.update( mockedCopyDto );
            fail("BookNotFoundException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(BookNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "book param is required" );
        }
    }

    @Test
    void copy_Update_WhenLibraryRequired_UT() {
        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        CopyDto mockedCopyDto = getMockedCopyDto(1, false);
        when( mockedCopyDto.getLibraryDto() ).thenReturn( null );

        try{
            copyServiceUnderTest.update( mockedCopyDto );
            fail("LibraryNotFoundException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(LibraryNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "library param is required" );
        }
    }

    @Test
    void copy_Update_WhenCopyIdRequired_UT() {
        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        CopyDto mockedCopyDto = getMockedCopyDto(1, false);
        when( mockedCopyDto.getId() ).thenReturn( null );

        try{
            copyServiceUnderTest.update( mockedCopyDto );
            fail("CopyNotFoundException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(CopyNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "copy id param is required" );
        }
    }

    @Test
    void copy_Update_WhenCopyNotFound_UT() {
        CopyDto mockedCopyDto = getMockedCopyDto(1, false);
        when( mockedCopyRepository.findById( any(Long.class ) ) ).thenReturn( Optional.empty() );
        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        try{
            copyServiceUnderTest.update( mockedCopyDto );
            fail("CopyNotFoundException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(CopyNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "copy " + mockedCopyDto.getId() + " not found" );
        }
    }

    @Test
    void copy_Update_WhenBookNotFound_UT() {
        CopyDto mockedCopyDto = getMockedCopyDto(1, false);
        Copy mockedCopy = getMockedCopy(1, false);

        when( mockedCopyRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of( mockedCopy ) );
        when( mockedBookRepository.findById( any(Long.class ) )  ).thenReturn( Optional.empty() );
        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        try{
            copyServiceUnderTest.update( mockedCopyDto );
            fail("BookNotFoundException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(BookNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "book " + mockedCopyDto.getBookDto().getBookId() + " not found" );
        }
    }

    @Test
    void copy_Update_WhenLibraryNotFound_UT() {
        CopyDto mockedCopyDto = getMockedCopyDto(1, false);
        Copy mockedCopy = getMockedCopy(1, false);
        Book mockedBook = getMockedBook(1);

        when( mockedCopyRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of( mockedCopy ) );
        when( mockedBookRepository.findById( any(Long.class ) )  ).thenReturn( Optional.of(mockedBook) );
        when( mockedLibraryRepository.findById( any(Long.class) ) ).thenReturn( Optional.empty() );

        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        try{
            copyServiceUnderTest.update( mockedCopyDto );
            fail("LibraryNotFoundException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(LibraryNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "library " + mockedCopyDto.getLibraryDto().getLibraryId() + " not found" );
        }
    }

    @Test
    void copy_Update_WhenSuccess_UT() {
        CopyDto mockedCopyDto = getMockedCopyDto(1, false);
        Copy mockedCopy = getMockedCopy(1, false);
        Book mockedBook = getMockedBook(1);
        Library mockedLibrary = getMockedLibrary();

        when( mockedCopyRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of( mockedCopy ) );
        when( mockedBookRepository.findById( any(Long.class ) )  ).thenReturn( Optional.of(mockedBook) );
        when( mockedLibraryRepository.findById( any(Long.class) ) ).thenReturn( Optional.of(mockedLibrary) );

        when( mockedCopyRepository.save( any(Copy.class) ) ).thenReturn( mockedCopy );

        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        try{
            CopyDto result = copyServiceUnderTest.update( mockedCopyDto );
            assertThat( result ).isInstanceOf( CopyDto.class );
            assertThat( result.getId() ).isEqualTo( mockedCopyDto.getId() );
            assertThat( result.isAvailable() ).isEqualTo( mockedCopyDto.isAvailable() );
            assertThat( result.getBookDto().getTitle() ).isEqualTo( mockedCopyDto.getBookDto().getTitle() );
            assertThat( result.getBookDto().getSummary() ).isEqualTo( mockedCopyDto.getBookDto().getSummary() );
            assertThat( result.getBookDto().getIsbn() ).isEqualTo( mockedCopyDto.getBookDto().getIsbn() );
            assertThat( result.getBookDto().getAuthorDto().getAuthorId() ).isEqualTo( mockedCopyDto.getBookDto().getAuthorDto().getAuthorId() );
            assertThat( result.getBookDto().getAuthorDto().getLastName() ).isEqualTo( mockedCopyDto.getBookDto().getAuthorDto().getLastName() );
            assertThat( result.getBookDto().getAuthorDto().getFirstName() ).isEqualTo( mockedCopyDto.getBookDto().getAuthorDto().getFirstName() );
            assertThat( result.getLibraryDto().getLibraryId() ).isEqualTo( mockedCopyDto.getLibraryDto().getLibraryId() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void copy_delete_WhenCopyIdRequired_UT() {
        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        try{
            copyServiceUnderTest.deleteById( null );
            fail("CopyNotFoundException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(CopyNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "copy id param is required" );
        }
    }

    @Test
    void copy_delete_WhenCopyNotFound_UT() {
        CopyDto mockedCopyDto = getMockedCopyDto(1, false);

        when( mockedCopyRepository.findById( any(Long.class) ) ).thenReturn( Optional.empty() );
        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        try{
            copyServiceUnderTest.deleteById( mockedCopyDto.getId() );
            fail("CopyNotFoundException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(CopyNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "copy " + mockedCopyDto.getId() + " not found" );
        }
    }

    @Test
    void copy_delete_WhenCopyHaveLoan_UT() {
        CopyDto mockedCopyDto = getMockedCopyDto(1, false);
        Copy mockedCopy = getMockedCopy( 1, false);

        when( mockedCopyRepository.findById( any(Long.class) ) ).thenReturn( Optional.of( mockedCopy ) );
        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        try{
            copyServiceUnderTest.deleteById( mockedCopyDto.getId() );
            fail("ImpossibleDeleteCopyException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(ImpossibleDeleteCopyException.class);
            assertThat( e.getMessage() ).isEqualTo( "This copy " + mockedCopyDto.getId() + " have existing loan" );
        }
    }

    @Test
    void copy_delete_WhenSuccess_UT() {
        Copy mockedCopy = getMockedCopy( 1, true);
        when( mockedCopyRepository.findById( any(Long.class) ) ).thenReturn( Optional.of( mockedCopy ) );
        copyServiceUnderTest = new CopyServiceImpl( mockedCopyRepository, mockedBookRepository, mockedLibraryRepository);

        try{
            copyServiceUnderTest.deleteById( any(Long.class) );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }
}