package com.emilie.Lib10.Services;

import com.emilie.Lib10.Exceptions.*;
import com.emilie.Lib10.Models.Dtos.*;
import com.emilie.Lib10.Models.Entities.*;
import com.emilie.Lib10.Repositories.BookRepository;
import com.emilie.Lib10.Repositories.CopyRepository;
import com.emilie.Lib10.Repositories.LibraryRepository;
import com.emilie.Lib10.Services.impl.BookServiceImpl;
import com.emilie.Lib10.Services.impl.CopyServiceImpl;
import com.emilie.Lib10.Services.impl.LibraryServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LibraryServiceImplUnitTest {

    LibraryServiceImpl libraryServiceUnderTest;
    LibraryRepository mockedLibraryRepository = mock (LibraryRepository.class);
    BookRepository mockedBookRepository = mock ( BookRepository.class );
    CopyRepository mockedCopyRepository = mock ( CopyRepository.class );

    private LibraryDto getMockedLibraryDto(int id){
        AddressDto mockedAddressDto = getMockedAddressDto();

        LibraryDto libraryDto = mock (LibraryDto.class );
        when( libraryDto.getLibraryId() ).thenReturn( Long.valueOf( id) );
        when( libraryDto.getName() ).thenReturn( "library NAME" );
        when( libraryDto.getPhoneNumber() ).thenReturn( "0312345678" );
        when( libraryDto.getAddressDto() ).thenReturn( mockedAddressDto );
        return libraryDto;
    }

    private Library getMockedLibrary(int id){
        Address mockedAddress = getMockedAddress();

        Library library = mock (Library.class );
        when( library.getLibraryId() ).thenReturn( Long.valueOf( id) );
        when( library.getName() ).thenReturn( "library NAME" );
        when( library.getPhoneNumber() ).thenReturn( "0312345678" );
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

    private CopyDto getMockedCopyDto(int id,  boolean availability){
        BookDto bookDto = getMockedBookDto( 1);

        CopyDto copyDto = mock ( CopyDto.class );
        when( copyDto.getId() ).thenReturn( Long.valueOf( id ));
        when( copyDto.isAvailable() ).thenReturn( availability );
        when( copyDto.getBookDto() ).thenReturn( bookDto );
        return copyDto;
    }

    private Copy getMockedCopy(int id, boolean availability){
        Book book = getMockedBook( 1 );

        Copy copy = mock ( Copy.class );
        when( copy.getId() ).thenReturn( Long.valueOf( id ));
        when( copy.isAvailable() ).thenReturn( availability );
        when( copy.getBook() ).thenReturn( book );
        return copy;
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
    void library_findAll_UT() {
        ArrayList<Library> libList = new ArrayList<>();
        when( mockedLibraryRepository.findAll() ).thenReturn( libList );
        try{
            libraryServiceUnderTest = new LibraryServiceImpl( mockedLibraryRepository, mockedBookRepository, mockedCopyRepository);

            assertThat( libraryServiceUnderTest.findAll() ).isInstanceOf( ArrayList.class );
            assertThat( libraryServiceUnderTest.findAll().size() ).isEqualTo( 0 );

            libList.add( getMockedLibrary(1));
            libList.add( getMockedLibrary(2));
            libList.add( getMockedLibrary(3));

            assertThat( libraryServiceUnderTest.findAll() ).isInstanceOf( ArrayList.class );
            assertThat( libraryServiceUnderTest.findAll().size() ).isEqualTo( 3 );

        }catch(Exception e ){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void library_findById_WhenNotFound_UT() {
        when( mockedLibraryRepository.findById( any(Long.class) ) ).thenReturn( Optional.empty() );
        libraryServiceUnderTest = new LibraryServiceImpl( mockedLibraryRepository, mockedBookRepository, mockedCopyRepository);

        try{
            libraryServiceUnderTest.findById( 9L );
            fail("LibraryNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( LibraryNotFoundException.class );
        }
    }

    @Test
    void library_FindById_WhenSuccess_UT() {
       Library mockedLibrary = getMockedLibrary(1);
        when( mockedLibraryRepository.findById( any(Long.class) ) ).thenReturn( Optional.of( mockedLibrary ) );
        libraryServiceUnderTest = new LibraryServiceImpl( mockedLibraryRepository, mockedBookRepository, mockedCopyRepository);

        try{
            LibraryDto result = libraryServiceUnderTest.findById( any(Long.class) );
            assertThat( result ).isInstanceOf( LibraryDto.class );
            assertThat( result.getLibraryId() ).isEqualTo( mockedLibrary.getLibraryId() );
            assertThat( result.getPhoneNumber() ).isEqualTo( mockedLibrary.getPhoneNumber() );
            assertThat( result.getName() ).isEqualTo( mockedLibrary.getName() );
            assertThat( result.getAddressDto().getCity() ).isEqualTo( mockedLibrary.getAddress().getCity() );
            assertThat( result.getAddressDto().getStreet() ).isEqualTo( mockedLibrary.getAddress().getStreet() );
            assertThat( result.getAddressDto().getZipCode() ).isEqualTo( mockedLibrary.getAddress().getZipCode() );
            assertThat( result.getAddressDto().getNumber() ).isEqualTo( mockedLibrary.getAddress().getNumber() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void library_create_whenAlreadyExist_UT() {
        Library mockedLibrary = getMockedLibrary(1 );
        when( mockedLibraryRepository.findByName( any(String.class) ) ).thenReturn( Optional.of( mockedLibrary ) );
        libraryServiceUnderTest = new LibraryServiceImpl( mockedLibraryRepository, mockedBookRepository, mockedCopyRepository);

        try{
            libraryServiceUnderTest.save( getMockedLibraryDto(1) );
            fail("LibraryAlreadyExistException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(LibraryAlreadyExistException.class);
            assertThat( e.getMessage() ).isEqualTo("library already exists");
        }
    }

    @Test
    void library_create_whenAddressNotFound_UT() {
        LibraryDto mockedLibraryDto = getMockedLibraryDto(1 );
        when( mockedLibraryDto.getAddressDto() ).thenReturn( null);
        when( mockedLibraryRepository.findByName( any(String.class) ) ).thenReturn( Optional.empty() );
        libraryServiceUnderTest = new LibraryServiceImpl( mockedLibraryRepository, mockedBookRepository, mockedCopyRepository);

        try{
            libraryServiceUnderTest.save( mockedLibraryDto );
            fail("AddressNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(AddressNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("Address not found");
        }
    }

    @Test
    void library_create_whenSuccess_UT() {
        LibraryDto mockedLibraryDto = getMockedLibraryDto(1 );
        Library mockedLibrary = getMockedLibrary(1 );
        when( mockedLibraryRepository.findByName( any(String.class) ) ).thenReturn( Optional.empty() );
        when( mockedLibraryRepository.save( any(Library.class ) ) ).thenReturn( mockedLibrary );
        libraryServiceUnderTest = new LibraryServiceImpl( mockedLibraryRepository, mockedBookRepository, mockedCopyRepository);

        try{
            LibraryDto result = libraryServiceUnderTest.save( mockedLibraryDto );
            assertThat( result ).isInstanceOf( LibraryDto.class );
            assertThat( result.getLibraryId() ).isEqualTo( mockedLibraryDto.getLibraryId() );
            assertThat( result.getPhoneNumber() ).isEqualTo( mockedLibraryDto.getPhoneNumber() );
            assertThat( result.getName() ).isEqualTo( mockedLibraryDto.getName() );
            assertThat( result.getAddressDto().getCity() ).isEqualTo( mockedLibraryDto.getAddressDto().getCity() );
            assertThat( result.getAddressDto().getStreet() ).isEqualTo( mockedLibraryDto.getAddressDto().getStreet() );
            assertThat( result.getAddressDto().getZipCode() ).isEqualTo( mockedLibraryDto.getAddressDto().getZipCode() );
            assertThat( result.getAddressDto().getNumber() ).isEqualTo( mockedLibraryDto.getAddressDto().getNumber() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void library_Update_WhenLibraryParamRequired_UT() {
        LibraryDto mockedLibraryDto = mock(LibraryDto.class );

        libraryServiceUnderTest = new LibraryServiceImpl( mockedLibraryRepository, mockedBookRepository, mockedCopyRepository);

        try{
            libraryServiceUnderTest.update( null );
            fail("LibraryNotFoundException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(LibraryNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "library id param is required" );
        }


        when( mockedLibraryDto.getLibraryId() ).thenReturn( null );
        try{
            libraryServiceUnderTest.update( mockedLibraryDto );
            fail("LibraryNotFoundException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(LibraryNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "library id param is required" );
        }
    }

    @Test
    void library_Update_WhenLibraryNotFound_UT() {
        LibraryDto mockedLibraryDto = mock(LibraryDto.class );

        when( mockedLibraryRepository.findById( any(Long.class ) ) ).thenReturn( Optional.empty() );
        libraryServiceUnderTest = new LibraryServiceImpl( mockedLibraryRepository, mockedBookRepository, mockedCopyRepository);

        try{
            libraryServiceUnderTest.update( mockedLibraryDto );
            fail("LibraryNotFoundException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(LibraryNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "library not found" );
        }
    }

    @Test
    void library_Update_WhenSuccess_UT() {
        LibraryDto mockedLibraryDto = getMockedLibraryDto(1);
        Library mockedLibrary = getMockedLibrary(1);
        when( mockedLibraryRepository.findById( any(Long.class) ) ).thenReturn( Optional.of( mockedLibrary ) );
        when( mockedLibraryRepository.save( any(Library.class) )).thenReturn( mockedLibrary );

        libraryServiceUnderTest = new LibraryServiceImpl( mockedLibraryRepository, mockedBookRepository, mockedCopyRepository);

        try{
            LibraryDto result = libraryServiceUnderTest.update( mockedLibraryDto );
            assertThat( result ).isInstanceOf( LibraryDto.class );
            assertThat( result.getLibraryId() ).isEqualTo( mockedLibraryDto.getLibraryId() );
            assertThat( result.getPhoneNumber() ).isEqualTo( mockedLibraryDto.getPhoneNumber() );
            assertThat( result.getName() ).isEqualTo( mockedLibraryDto.getName() );
            assertThat( result.getAddressDto().getCity() ).isEqualTo( mockedLibraryDto.getAddressDto().getCity() );
            assertThat( result.getAddressDto().getStreet() ).isEqualTo( mockedLibraryDto.getAddressDto().getStreet() );
            assertThat( result.getAddressDto().getZipCode() ).isEqualTo( mockedLibraryDto.getAddressDto().getZipCode() );
            assertThat( result.getAddressDto().getNumber() ).isEqualTo( mockedLibraryDto.getAddressDto().getNumber() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void library_delete_WhenLibraryNotFound_UT() {
        LibraryDto mockedLibraryDto = getMockedLibraryDto(1);

        when( mockedLibraryRepository.findById( any(Long.class) ) ).thenReturn( Optional.empty() );
        libraryServiceUnderTest = new LibraryServiceImpl( mockedLibraryRepository, mockedBookRepository, mockedCopyRepository);

        try{
            libraryServiceUnderTest.deleteById( mockedLibraryDto.getLibraryId() );
            fail("LibraryNotFoundException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(LibraryNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "library not found" );
        }
    }

    @Test
    void library_delete_WhenLibraryHaveCopy_UT() {
        LibraryDto mockedLibraryDto = getMockedLibraryDto(1);
        Library mockedLibrary = getMockedLibrary(1);
        HashSet<Copy> copies = new HashSet<>();
        copies.add( mock( Copy.class ) );
        when( mockedLibrary.getCopies() ).thenReturn( copies );

        when( mockedLibraryRepository.findById( any(Long.class) ) ).thenReturn( Optional.of(mockedLibrary) );

        libraryServiceUnderTest = new LibraryServiceImpl( mockedLibraryRepository, mockedBookRepository, mockedCopyRepository);

        try{
            libraryServiceUnderTest.deleteById( mockedLibraryDto.getLibraryId() );
            fail("ImpossibleDeleteLibraryException expected but not thrown");
        }catch(Exception e){
            assertThat(e).isInstanceOf(ImpossibleDeleteLibraryException.class);
            assertThat( e.getMessage() ).isEqualTo( "This library " + mockedLibraryDto.getLibraryId() + " have existing copies" );
        }
    }

    @Test
    void library_delete_WhenSuccess_UT() {
        LibraryDto mockedLibraryDto = getMockedLibraryDto(1);
        Library mockedLibrary = getMockedLibrary(1);

        when( mockedLibraryRepository.findById( any(Long.class) ) ).thenReturn( Optional.of(mockedLibrary) );

        libraryServiceUnderTest = new LibraryServiceImpl( mockedLibraryRepository, mockedBookRepository, mockedCopyRepository);

        try{
            libraryServiceUnderTest.deleteById( mockedLibraryDto.getLibraryId() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void library_findCopiesByLibrary_UT() {
        LibraryDto mockedLibraryDto = getMockedLibraryDto(1);
        Library mockedLibrary = getMockedLibrary(1);
        HashSet<Copy> copies = new HashSet<>();
        copies.add( getMockedCopy(1, true) );
        copies.add( getMockedCopy(2, true) );
        copies.add( getMockedCopy(3, false));
        copies.add( getMockedCopy(4, true) );
        copies.add( getMockedCopy(5, false));
        copies.add( getMockedCopy(6, true) );
        copies.add( getMockedCopy(7, true) );
        when( mockedLibrary.getCopies() ).thenReturn( copies );

        when( mockedLibraryRepository.findById( any(Long.class) ) ).thenReturn( Optional.of(mockedLibrary) );

        libraryServiceUnderTest = new LibraryServiceImpl( mockedLibraryRepository, mockedBookRepository, mockedCopyRepository);

        try{
            Set<CopyDto> resultCopies = libraryServiceUnderTest.findCopiesByLibraryId( mockedLibraryDto.getLibraryId() );
            assertThat( resultCopies ).isInstanceOf(Set.class);
            assertThat( resultCopies.size() ).isEqualTo( 7 );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }
}
