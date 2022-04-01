package com.emilie.Lib10.Services;

import com.emilie.Lib10.Exceptions.*;
import com.emilie.Lib10.Models.Dtos.*;
import com.emilie.Lib10.Models.Entities.*;
import com.emilie.Lib10.Repositories.CopyRepository;
import com.emilie.Lib10.Repositories.LoanRepository;
import com.emilie.Lib10.Repositories.UserRepository;
import com.emilie.Lib10.Services.impl.LoanServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoanServiceImplUnitTest {

    LoanServiceImpl loanServiceUnderTest;

    LoanRepository mockedLoanRepository = mock( LoanRepository.class );
    UserRepository mockedUserRepository = mock( UserRepository.class );
    CopyRepository mockedCopyRepository = mock( CopyRepository.class );

    private LoanDto getMockedLoanDto(int id, boolean isExtended, boolean isReturned ){
        CopyDto mockedCopyDto = getMockedCopyDto(1, true);
        UserDto mockedUserDto = getMockedUserDto(1);

        LoanDto loanDto = mock(LoanDto.class);
        when( loanDto.getId() ).thenReturn( Long.valueOf( id ));
        when( loanDto.isExtended() ).thenReturn( isExtended );
        when( loanDto.isReturned() ).thenReturn( isReturned );

        when( loanDto.getUserDto() ).thenReturn( mockedUserDto );
        when( loanDto.getCopyDto() ).thenReturn( mockedCopyDto );

        return loanDto;
    }

    private Loan getMockedLoan(int id, boolean isExtended, boolean isReturned){
        Copy mockedCopy = getMockedCopy(1, true);
        User mockedUser = getMockedUser(1);

        Loan loan = mock(Loan.class);
        when( loan.getId() ).thenReturn( Long.valueOf( id ));
        when( loan.isExtended() ).thenReturn( isExtended );
        when( loan.isReturned() ).thenReturn( isReturned );

        when( loan.getUser() ).thenReturn( mockedUser );
        when( loan.getCopy() ).thenReturn( mockedCopy );

        return loan;
    }

    private UserDto getMockedUserDto(int id){
        UserDto userDto = mock( UserDto.class );
        when( userDto.getUserId() ).thenReturn( Long.valueOf( id ) );
        when( userDto.getEmail() ).thenReturn( "User@email.com" );
        when( userDto.getFirstName() ).thenReturn( "user firstname" );
        when( userDto.getLastName() ).thenReturn( "user lastname" );
        return userDto;
    }

    private User getMockedUser(int id){
        User user = mock( User.class );
        when( user.getId() ).thenReturn( Long.valueOf( id ) );
        when( user.getEmail() ).thenReturn( "User@email.com" );
        when( user.getFirstName() ).thenReturn( "user firstname" );
        when( user.getLastName() ).thenReturn( "user lastname" );
        return user;
    }

    private CopyDto getMockedCopyDto(int id, boolean availability){
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
    void loan_findAll_UT() {
        List<Loan> loanList = new ArrayList<>();
        when( mockedLoanRepository.findAll() ).thenReturn( loanList );
        try{
            loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

            assertThat( loanServiceUnderTest.findAll() ).isInstanceOf( ArrayList.class );
            assertThat( loanServiceUnderTest.findAll().size() ).isEqualTo( 0 );

            loanList.add( getMockedLoan(1, false, false));
            loanList.add( getMockedLoan(2, false, false));
            loanList.add( getMockedLoan(3, false, false));

            assertThat( loanServiceUnderTest.findAll() ).isInstanceOf( ArrayList.class );
            assertThat( loanServiceUnderTest.findAll().size() ).isEqualTo( 3 );


        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void loan_findById_WhenNotFound_UT() {
        when( mockedLoanRepository.findById( any(Long.class) ) ).thenReturn( Optional.empty() );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.findById( 9L );
            fail("LoanNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( LoanNotFoundException.class );
        }
    }

    @Test
    void loan_FindById_WhenSuccess_UT() {
        Loan mockedLoan = getMockedLoan(1 , false, false);
        when( mockedLoanRepository.findById( any(Long.class) ) ).thenReturn( Optional.of( mockedLoan ) );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            LoanDto result = loanServiceUnderTest .findById( any(Long.class) );
            assertThat( result ).isInstanceOf( LoanDto.class );
            assertThat( result.getId() ).isEqualTo( mockedLoan.getId() );
            assertThat( result.isReturned() ).isEqualTo( mockedLoan.isReturned() );
            assertThat( result.isExtended() ).isEqualTo( mockedLoan.isExtended() );
            assertThat( result.getCopyDto().getBookDto().getTitle() ).isEqualTo( mockedLoan.getCopy().getBook().getTitle() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void loan_create_WhenLoanRequired_UT() {

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.save( null );
            fail("LoanNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(LoanNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("loanDto id param is required");
        }
    }

    @Test
    void loan_create_WhenUserRequired_UT() {

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);
        when( mockedLoanDto.getUserDto() ).thenReturn( null );
        try{
            loanServiceUnderTest.save( mockedLoanDto );
            fail("UserNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(UserNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("user id param is required");
        }
    }

    @Test
    void loan_create_WhenCopyRequired_UT() {
        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);
        when( mockedLoanDto.getCopyDto() ).thenReturn( null );
        try{
            loanServiceUnderTest.save( mockedLoanDto );
            fail("CopyNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(CopyNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("copy id param is required");
        }
    }

    @Test
    void loan_create_WhenUserNotFound_UT() {
        when( mockedUserRepository.findById( any(Long.class) ) ).thenReturn( Optional.empty() );
        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);
        try{
            loanServiceUnderTest.save( mockedLoanDto );
            fail("UserNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(UserNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("user " + mockedLoanDto.getUserDto().getUserId() + " not found" );
        }
    }

    @Test
    void loan_create_WhenCopyNotFound_UT() {
        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);
        User mockedUser = getMockedUser(1);
        when( mockedUserRepository.findById( any(Long.class) ) ).thenReturn( Optional.of(mockedUser) );
        when( mockedCopyRepository.findById( any(Long.class) ) ).thenReturn( Optional.empty() );
        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.save( mockedLoanDto );
            fail("CopyNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(CopyNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("copy " + mockedLoanDto.getCopyDto().getId() + " not found" );
        }
    }

    @Test
    void loan_create_WhenAlreadyExist_UT() {
        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);
        User mockedUser = getMockedUser(1);
        Copy mockedCopy = getMockedCopy(1, false);
        when( mockedUserRepository.findById( any(Long.class) ) ).thenReturn( Optional.of(mockedUser) );
        when( mockedCopyRepository.findById( any(Long.class) ) ).thenReturn( Optional.of(mockedCopy) );

        Loan mockedLoan = getMockedLoan(1,false, false);
        when( mockedLoanRepository.findNotReturnedByCopyId( any(Long.class ) ) ).thenReturn( Optional.of( mockedLoan ) );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.save( mockedLoanDto );
            fail("LoanAlreadyExistsException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(LoanAlreadyExistsException.class);
            assertThat( e.getMessage() ).isEqualTo("loan for copy " + mockedLoanDto.getCopyDto().getId() + " already exists");
        }
    }

    @Test
    void loan_create_WhenSuccess_UT() {
        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);
        User mockedUser = getMockedUser(1);
        Copy mockedCopy = getMockedCopy(1, false);
        when( mockedUserRepository.findById( any(Long.class) ) ).thenReturn( Optional.of(mockedUser) );
        when( mockedCopyRepository.findById( any(Long.class) ) ).thenReturn( Optional.of(mockedCopy) );

        when( mockedLoanRepository.findNotReturnedByCopyId( any(Long.class ) ) ).thenReturn( Optional.empty() );

        Loan mockedLoan = getMockedLoan(1,false, false);
        when( mockedLoanRepository.save( any(Loan.class) ) ).thenReturn( mockedLoan );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);


        try{
            LoanDto result = loanServiceUnderTest.save( mockedLoanDto );
            assertThat( result ).isInstanceOf( LoanDto.class );
            assertThat( result.getId() ).isEqualTo( mockedLoanDto.getId() );
            assertThat( result.isReturned() ).isEqualTo( mockedLoanDto.isReturned() );
            assertThat( result.isExtended() ).isEqualTo( mockedLoanDto.isExtended() );
            assertThat( result.getCopyDto().getBookDto().getTitle() ).isEqualTo( mockedLoanDto.getCopyDto().getBookDto().getTitle() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void loan_findByUser_UT() {
        UserDto mockedUser = getMockedUserDto(1);
        List<Loan> loansResult = new ArrayList<>();

        when( mockedLoanRepository.findLoansByUserId( any(Long.class ) ) ).thenReturn( loansResult );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);
        try{
            loanServiceUnderTest.findLoansByUserId( mockedUser.getUserId() );
            fail("LoanNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(LoanNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("Loan not found");
        }

        loansResult.add( getMockedLoan( 1, true, true) );
        loansResult.add( getMockedLoan( 2, false, true) );
        loansResult.add( getMockedLoan( 3, true, false ) );
        loansResult.add( getMockedLoan( 4, false, false ) );

        when( mockedLoanRepository.findLoansByUserId( any(Long.class ) ) ).thenReturn( loansResult );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            List<LoanDto> resultList = loanServiceUnderTest.findLoansByUserId( mockedUser.getUserId() );
            assertThat( resultList ).isInstanceOf( ArrayList.class );
            assertThat( resultList.size() ).isEqualTo( 4 );
        }catch( Exception e ){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void loan_update_whenLoanNotFound_UT() {
        LoanDto mockedLoanDto = getMockedLoanDto(1, true, false);
        when( mockedLoanRepository.findById( any(Long.class ) ) ).thenReturn( Optional.empty() );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.update( mockedLoanDto );
            fail("LoanNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(LoanNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("loan not found");
        }
    }

    @Test
    void loan_update_whenUserNotFound_UT() {
        LoanDto mockedLoanDto = getMockedLoanDto(1, true, false);

        Loan mockedLoan = getMockedLoan( 1, false, false);
        when( mockedLoanRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of(mockedLoan) );
        when( mockedUserRepository.findById( any(Long.class ) ) ).thenReturn( Optional.empty() );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.update( mockedLoanDto );
            fail("UserNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(UserNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("user not found");
        }
    }

    @Test
    void loan_update_whenCopyNotFound_UT() {
        LoanDto mockedLoanDto = getMockedLoanDto(1, true, false);

        Loan mockedLoan = getMockedLoan( 1, false, false);
        when( mockedLoanRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of(mockedLoan) );
        User mockedUser = getMockedUser(1);
        when( mockedUserRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of( mockedUser ) );
        when( mockedCopyRepository.findById( any(Long.class ) ) ).thenReturn( Optional.empty() );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.update( mockedLoanDto );
            fail("CopyNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(CopyNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("copy not found");
        }
    }

    @Test
    void loan_update_whenSuccess_UT() {
        LoanDto mockedLoanDto = getMockedLoanDto(1, true, false);

        Loan mockedLoan = getMockedLoan( 1, false, false);
        when( mockedLoanRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of(mockedLoan) );
        User mockedUser = getMockedUser(1);
        when( mockedUserRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of( mockedUser ) );
        Copy mockedCopy = getMockedCopy(1, false);
        when( mockedCopyRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of(mockedCopy) );

        Loan mockedUpdatedLoan = getMockedLoan(1, true, false);
        when( mockedLoanRepository.save( any(Loan.class ) ) ) .thenReturn( mockedUpdatedLoan );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            LoanDto updateResult = loanServiceUnderTest.update( mockedLoanDto );
            assertThat( updateResult ).isInstanceOf( LoanDto.class );
            assertThat( updateResult.getId() ).isEqualTo( mockedLoanDto.getId() );
            assertThat( updateResult.isReturned() ).isEqualTo( mockedLoanDto.isReturned() );
            assertThat( updateResult.isExtended() ).isEqualTo( mockedLoanDto.isExtended() );
            assertThat( updateResult.getCopyDto().getBookDto().getTitle() ).isEqualTo( mockedLoanDto.getCopyDto().getBookDto().getTitle() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void loan_extend_WhenLoanNotFound_UT() {
        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);
        when( mockedLoanRepository.findById( any(Long.class ) ) ).thenReturn( Optional.empty() );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.extendLoan( mockedLoanDto.getId() );
            fail("LoanNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(LoanNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("loan " + mockedLoanDto.getId() + " not found");
        }
    }

    @Test
    void loan_extend_WhenLoanAlreadyExtented_UT() {
        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);

        Loan mockedLoan = getMockedLoan(1, true, false);
        when( mockedLoanRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of(mockedLoan) );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.extendLoan( mockedLoanDto.getId() );
            fail("ImpossibleExtendLoanException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(ImpossibleExtendLoanException.class);
            assertThat( e.getMessage() ).isEqualTo("this loan " + mockedLoanDto.getId() + " has already been extended");
        }
    }

    @Test
    void loan_extend_WhenLoanExpiredEndDate_UT() {
        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);

        Loan mockedLoan = getMockedLoan(1, false, false);
        LocalDate localDate=LocalDate.now();
        Instant instant= localDate.minusDays( 1L ).atStartOfDay( ZoneId.systemDefault() ).toInstant();
        when( mockedLoan.getLoanEndDate() ).thenReturn( Date.from( instant ) );
        when( mockedLoanRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of(mockedLoan) );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.extendLoan( mockedLoanDto.getId() );
            fail("ImpossibleExtendLoanException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(ImpossibleExtendLoanException.class);
            assertThat( e.getMessage() ).isEqualTo("unauthorize extend, loan's endDate is expired");
        }
    }

    @Test
    void loan_extend_WhenSuccess_UT() {
        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);

        Loan mockedLoan = getMockedLoan(1, false, false);
        LocalDate localDate=LocalDate.now();
        Instant instant= localDate.atStartOfDay( ZoneId.systemDefault() ).toInstant();
        when( mockedLoan.getLoanEndDate() ).thenReturn( Date.from( instant ) );
        when( mockedLoanRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of(mockedLoan) );

        Loan mockedLoanUpdated = getMockedLoan(1, true, false);
        when( mockedLoanRepository.save( any(Loan.class )   )   ).thenReturn( mockedLoanUpdated );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            LoanDto result = loanServiceUnderTest.extendLoan( mockedLoanDto.getId() );
            assertThat( result ).isInstanceOf( LoanDto.class );
            assertThat( result.getId() ).isEqualTo( mockedLoanDto.getId() );
            assertThat( result.isReturned() ).isEqualTo( mockedLoanDto.isReturned() );
            assertThat( result.isExtended() ).isEqualTo( true );
            assertThat( result.getCopyDto().getBookDto().getTitle() ).isEqualTo( mockedLoanDto.getCopyDto().getBookDto().getTitle() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void loan_return_whenLoanNotFound_UT() {
        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);
        when( mockedLoanRepository.findById( any(Long.class ) ) ).thenReturn( Optional.empty() );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.returnLoan( mockedLoanDto.getId() );
            fail("LoanNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(LoanNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("loan " + mockedLoanDto.getId() + " not found");
        }
    }

    @Test
    void loan_return_whenSuccess_UT() {
        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);
        Loan mockedLoan = getMockedLoan(1, false, true );

        when( mockedLoanRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of(mockedLoan) );

        Loan mockedLoanUpdated = getMockedLoan(1, false, true);
        when( mockedLoanRepository.save( any(Loan.class )   )   ).thenReturn( mockedLoanUpdated );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            LoanDto result = loanServiceUnderTest.returnLoan( mockedLoanDto.getId() );
            assertThat( result ).isInstanceOf( LoanDto.class );
            assertThat( result.getId() ).isEqualTo( mockedLoanDto.getId() );
            assertThat( result.isReturned() ).isEqualTo( true );
            assertThat( result.isExtended() ).isEqualTo( false );
            assertThat( result.getCopyDto().getBookDto().getTitle() ).isEqualTo( mockedLoanDto.getCopyDto().getBookDto().getTitle() );
            assertThat( result.getCopyDto().isAvailable()).isEqualTo( true );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void loan_findDelay_UT() {
        List<Loan> loanList = new ArrayList<>();

        when( mockedLoanRepository.searchAllDelay() ).thenReturn( loanList );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            List<LoanDto> resultList = loanServiceUnderTest.findDelay( );
            assertThat( resultList ).isInstanceOf( ArrayList.class );
            assertThat( resultList.size() ).isEqualTo( 0 );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }


        loanList.add( getMockedLoan( 1, true, true) );
        loanList.add( getMockedLoan( 2, false, true) );
        loanList.add( getMockedLoan( 3, true, false ) );
        loanList.add( getMockedLoan( 4, false, false ) );
        when( mockedLoanRepository.searchAllDelay() ).thenReturn( loanList );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            List<LoanDto> resultList = loanServiceUnderTest.findDelay( );
            assertThat( resultList ).isInstanceOf( ArrayList.class );
            assertThat( resultList.size() ).isEqualTo( 4 );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void loan_delete_WhenLoanNotFound_UT() {
        Loan mockedLoan = getMockedLoan(1, false, true);

        when( mockedLoanRepository.findById( any(Long.class ) ) ).thenReturn( Optional.empty()  );
        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.deleteById( mockedLoan.getId() );
            fail("LoanNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(LoanNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo("loan " + mockedLoan.getId() + " not found");
        }
    }

    @Test
    void loan_delete_WhenLoanNotReturned_UT() {
        Loan mockedLoan = getMockedLoan(1, false, false);

        when( mockedLoanRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of(mockedLoan)  );
        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.deleteById( mockedLoan.getId() );
            fail("ImpossibleDeleteLoanException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(ImpossibleDeleteLoanException.class);
            assertThat( e.getMessage() ).isEqualTo("copy of loan " + mockedLoan.getId() + " have not been returned");
        }
    }

    @Test
    void loan_delete_WhenSuccess_UT() {
        Loan mockedLoan = getMockedLoan(1, false, true);

        when( mockedLoanRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of(mockedLoan)  );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.deleteById( mockedLoan.getId() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void loan_haveAccess_WhenUnauthorized_UT() {
        //bad userId
        UserDto mockedUserDto = getMockedUserDto(5);
        when( mockedUserDto.getRoles() ).thenReturn( "CUSTOMER" );

        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.haveAccess( mockedUserDto, mockedLoanDto );
            fail("UnauthorizedException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(UnauthorizedException.class);
            assertThat( e.getMessage() ).isEqualTo( "access denied" );
        }
    }

    @Test
    void loan_haveAccess_WhenOwnerSuccess_UT() {
        UserDto mockedUserDto = getMockedUserDto(1);
        when( mockedUserDto.getRoles() ).thenReturn( "CUSTOMER" );

        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.haveAccess( mockedUserDto, mockedLoanDto );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void loan_haveAccess_WhenAdminSuccess_UT() {
        //Bad userId, but ADMIN
        UserDto mockedUserDto = getMockedUserDto(5);
        when( mockedUserDto.getRoles() ).thenReturn( "ADMIN" );

        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.haveAccess( mockedUserDto, mockedLoanDto );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void loan_haveAccess_WhenEmployeeSuccess_UT() {
        //Bad userId, but EMPLOYEE
        UserDto mockedUserDto = getMockedUserDto(5);
        when( mockedUserDto.getRoles() ).thenReturn( "EMPLOYEE" );

        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.haveAccess( mockedUserDto, mockedLoanDto );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void loan_isValid_UserRequired_UT() {
        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);
        when( mockedLoanDto.getUserDto() ).thenReturn( null );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.isValid( mockedLoanDto );
            fail("UserNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(UserNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "user param is required" );
        }
    }

    @Test
    void loan_isValid_CopyRequired_UT() {
        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);
        when( mockedLoanDto.getCopyDto() ).thenReturn( null );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.isValid( mockedLoanDto );
            fail("CopyNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(CopyNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "copy param is required" );
        }
    }

    @Test
    void loan_isValid_BookRequiredForCopyParam_UT() {
        LoanDto mockedLoanDto = getMockedLoanDto(1, false, false);
        CopyDto mockedCopyDto = getMockedCopyDto( 1, false);
        when( mockedCopyDto.getBookDto() ).thenReturn( null );
        when( mockedLoanDto.getCopyDto() ).thenReturn( mockedCopyDto );

        loanServiceUnderTest = new LoanServiceImpl(mockedLoanRepository, mockedUserRepository, mockedCopyRepository);

        try{
            loanServiceUnderTest.isValid( mockedLoanDto );
            fail("BookNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(BookNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "book param in copy param is required" );
        }
    }
}
