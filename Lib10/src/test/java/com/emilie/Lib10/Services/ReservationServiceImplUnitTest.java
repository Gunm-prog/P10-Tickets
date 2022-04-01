package com.emilie.Lib10.Services;

import com.emilie.Lib10.Exceptions.*;
import com.emilie.Lib10.Models.Dtos.*;
import com.emilie.Lib10.Models.Entities.*;
import com.emilie.Lib10.Repositories.BookRepository;
import com.emilie.Lib10.Repositories.ReservationRepository;
import com.emilie.Lib10.Repositories.UserRepository;
import com.emilie.Lib10.Services.impl.ReservationServiceImpl;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReservationServiceImplUnitTest {

    ReservationServiceImpl reservationServiceUnderTest;
    ReservationRepository mockedReservationRepository = mock( ReservationRepository.class );
    UserRepository mockedUserRepository = mock( UserRepository.class );
    BookRepository mockedBookRepository = mock( BookRepository.class );

    private Reservation getMockedReservation(int id, boolean isActive){
        User mockedUser = getMockedUser( 1 );
        Book mockedBook = getMockedBook( 1 );
        Reservation reservation = mock (Reservation.class);

        when( reservation.getId() ).thenReturn( Long.valueOf(id) );
        when( reservation.isActive() ).thenReturn( isActive );

        when( reservation.getUser() ).thenReturn( mockedUser );
        when( reservation.getBook() ).thenReturn( mockedBook );

        return reservation;
    }

    private ReservationDto getMockedReservationDto(int id, boolean isActive){
        UserDto mockedUserDto = getMockedUserDto( 1 );
        BookDto mockedBookDto = getMockedBookDto( 1 );
        ReservationDto reservationDto = mock (ReservationDto.class);

        when( reservationDto.getId() ).thenReturn( Long.valueOf(id) );
        when( reservationDto.isActive() ).thenReturn( isActive );

        when( reservationDto.getUserDto() ).thenReturn( mockedUserDto );
        when( reservationDto.getBookDto() ).thenReturn( mockedBookDto );

        return reservationDto;
    }

    private User getMockedUser(int id){
        User user = mock (User.class);
        when( user.getId() ).thenReturn( Long.valueOf( id ) );
        when( user.getEmail() ).thenReturn( "user@mail.com" );
        when( user.getFirstName() ).thenReturn( "user firstname" );
        when( user.getLastName() ).thenReturn( "user lastname" );
        return user;
    }

    private UserDto getMockedUserDto(int id){
        UserDto userDto = mock (UserDto.class);
        when( userDto.getUserId() ).thenReturn( Long.valueOf( id ) );
        when( userDto.getEmail() ).thenReturn( "user@mail.com" );
        when( userDto.getFirstName() ).thenReturn( "user firstname" );
        when( userDto.getLastName() ).thenReturn( "user lastname" );
        return userDto;
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

    /*
      LocalDate localDate=LocalDate.now();
        Instant instant= localDate.minusDays( 1L ).atStartOfDay( ZoneId.systemDefault() ).toInstant();
        when( mockedLoan.getLoanEndDate() ).thenReturn( Date.from( instant ) );
     */

    /*private Reservation makeRsvListForReservation(Reservation rsv){

        Reservation mockedUserReservation = getMockedReservation(1, false);

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
    }*/

    @Test
    void reservation_getUserPosition_UT() {
        try{
            Book mockedBook = getMockedBook(1);

            Reservation mockedUserReservation = getMockedReservation(1, false);

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

            reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

            assertThat( reservationServiceUnderTest.getUserPosition( mockedUserReservation ) ).isInstanceOf( Integer.class );
            assertThat( reservationServiceUnderTest.getUserPosition( mockedUserReservation ) ).isEqualTo( 2 );

        }catch(Exception e ){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void reservation_findAll_UT() {
        ArrayList<Reservation> rsvList = new ArrayList<>();
        when( mockedReservationRepository.findAll() ).thenReturn( rsvList );
        try{
            reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

            assertThat( reservationServiceUnderTest.findAll() ).isInstanceOf( ArrayList.class );
            assertThat( reservationServiceUnderTest.findAll().size() ).isEqualTo( 0 );

            rsvList.add( getMockedReservation(1, false));
            rsvList.add( getMockedReservation(2, false));
            rsvList.add( getMockedReservation(3, true));

            assertThat( reservationServiceUnderTest.findAll() ).isInstanceOf( ArrayList.class );
            assertThat( reservationServiceUnderTest.findAll().size() ).isEqualTo( 3 );

        }catch(Exception e ){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void reservation_findById_WhenNotFound_UT() {
        when( mockedReservationRepository.findById( any(Long.class) ) ).thenReturn( Optional.empty() );
        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.findById( any(Long.class) );
            fail("ReservationNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( ReservationNotFoundException.class );
        }
    }

    @Test
    void reservation_FindById_WhenSuccess_UT() {
        Reservation mockedReservation = getMockedReservation(1, false);
        when( mockedReservationRepository.findById( any(Long.class) ) ).thenReturn( Optional.of( mockedReservation ) );


        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            ReservationDto result = reservationServiceUnderTest.findById( mockedReservation.getId() );
            assertThat( result ).isInstanceOf( ReservationDto.class );
            assertThat( result.getId() ).isEqualTo( mockedReservation.getId() );
            assertThat( result.getNmbReservation() ).isInstanceOf( Integer.class );
            assertThat( result.getUserDto() ).isInstanceOf( UserDto.class );
            assertThat( result.getUserDto().getUserId() ).isEqualTo( mockedReservation.getUser().getId() );
            assertThat( result.getBookDto() ).isInstanceOf( BookDto.class );
            assertThat( result.getBookDto().getBookId() ).isEqualTo( mockedReservation.getBook().getBookId() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void reservation_create_WhenUserNotFound_UT() {

        ReservationDto mockedReservationDto = getMockedReservationDto(1,false);

        when( mockedUserRepository.findById( any(Long.class ) ) ).thenReturn( Optional.empty() );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.create( mockedReservationDto );
            fail("UserNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( UserNotFoundException.class );
            assertThat( e.getMessage() ).isEqualTo( "user " + mockedReservationDto.getUserDto().getUserId() + " not found"  );
        }

    }

    @Test
    void reservation_create_WhenBookNotFound_UT() {

        ReservationDto mockedReservationDto = getMockedReservationDto(1,false);

        User mockedUser = getMockedUser(1);
        when( mockedUserRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of(mockedUser) );

        when( mockedBookRepository.findById( any(Long.class ) ) ).thenReturn( Optional.empty() );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.create( mockedReservationDto );
            fail("BookNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( BookNotFoundException.class );
            assertThat( e.getMessage() ).isEqualTo( "book " + mockedReservationDto.getBookDto().getBookId() + " not found"  );
        }

    }

    @Test
    void reservation_create_WhenUserAlreadyLoanTheBook_UT() {

        ReservationDto mockedReservationDto = getMockedReservationDto(1,false);

        Book mockedReservedBook = getMockedBook(1);
        User mockedUser = getMockedUser(1);
        HashSet<Loan> userLoans = new HashSet<>();
            for(int i=1; i<3; i++){
                Loan loan = mock( Loan.class );
                Copy mockedCopy = mock( Copy.class );
                if(i==1){
                    when( mockedCopy.getBook() ).thenReturn( mockedReservedBook );
                }else{
                    Book mockedBook = getMockedBook(i);
                    when( mockedCopy.getBook() ).thenReturn( mockedBook );
                }
                when( loan.getCopy() ).thenReturn( mockedCopy );
                when( loan.isReturned() ).thenReturn( false );
                userLoans.add( loan );
            }
        when( mockedUser.getLoans() ).thenReturn( userLoans );

        when( mockedUserRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of(mockedUser) );

        when( mockedBookRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of( mockedReservedBook ) );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.create( mockedReservationDto );
            fail("UnauthorizedException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( UnauthorizedException.class );
            assertThat( e.getMessage() ).isEqualTo( "this book is already loaned by this user"  );
        }
    }

    @Test
    void reservation_create_WhenCopyAvailableForTheBook_UT() {

        ReservationDto mockedReservationDto = getMockedReservationDto(1,false);

        Book mockedReservedBook = getMockedBook(1);
        User mockedUser = getMockedUser(1);
        HashSet<Copy> copies = new HashSet<>();
        for(int i=1; i<3; i++) {
            Copy mockedCopy = mock(Copy.class);
            if(i==1){
                when(mockedCopy.isAvailable()).thenReturn(false);
            }else{
                when(mockedCopy.isAvailable()).thenReturn(true);
            }
            copies.add(mockedCopy);
        }

        when( mockedReservedBook.getCopies() ).thenReturn( copies );

        when( mockedUserRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of(mockedUser) );

        when( mockedBookRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of( mockedReservedBook ) );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.create( mockedReservationDto );
            fail("UnauthorizedException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( UnauthorizedException.class );
            assertThat( e.getMessage() ).isEqualTo( "a copy for this book is available, reservation isn't enabled"  );
        }
    }

    @Test
    void reservation_created_whenAlreadyExist_UT() {
        ReservationDto mockedReservationDto = getMockedReservationDto(1,false);

        User mockedUser = getMockedUser(1);
        when( mockedUserRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of(mockedUser) );

        Book mockedBook = getMockedBook(1);
        HashSet<Copy> copies = new HashSet<>();
        for(int i=0; i<4; i++) {
            Copy mockedCopy = mock(Copy.class);
                when(mockedCopy.isAvailable()).thenReturn(false);
            copies.add(mockedCopy);
        }

        when( mockedBook.getCopies() ).thenReturn( copies );
        when( mockedBookRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of( mockedBook ) );

        List<Reservation> bookReservations = new ArrayList<>();
        for(int i=0; i<2; i++){
            Reservation mockedReservation = getMockedReservation(i,false);
            if(i==1){
                when( mockedReservation.getUser() ).thenReturn( mockedUser );
            }else{
                when( mockedReservation.getUser() ).thenReturn( mock(User.class ) );
            }
            bookReservations.add( mockedReservation );
        }
        when( mockedReservationRepository.findByBookId( any(Long.class ) ) ).thenReturn( bookReservations );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.create( mockedReservationDto );
            fail("ReservationAlreadyExistException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( ReservationAlreadyExistException.class );
            assertThat( e.getMessage() ).isEqualTo( "the book is already reserved by this user"  );
        }
    }

    @Test
    void reservation_created_whenMaxLimitReservation_UT() {
        ReservationDto mockedReservationDto = getMockedReservationDto(1,false);

        User mockedUser = getMockedUser(1);
        when( mockedUserRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of(mockedUser) );

        Book mockedBook = getMockedBook(1);
        HashSet<Copy> copies = new HashSet<>();
        for(int i=0; i<1; i++) {
            Copy mockedCopy = mock(Copy.class);
            when(mockedCopy.isAvailable()).thenReturn(false);
            copies.add(mockedCopy);
        }

        when( mockedBook.getCopies() ).thenReturn( copies );
        when( mockedBookRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of( mockedBook ) );

        List<Reservation> bookReservations = new ArrayList<>();
        for(int i=0; i<2; i++){
            Reservation mockedReservation = getMockedReservation(i,false);
                when( mockedReservation.getUser() ).thenReturn( mock(User.class ) );
            bookReservations.add( mockedReservation );
        }
        when( mockedReservationRepository.findByBookId( any(Long.class ) ) ).thenReturn( bookReservations );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.create( mockedReservationDto );
            fail("MaxResaAtteintException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( MaxResaAtteintException.class );
            assertThat( e.getMessage() ).isEqualTo( "reservation list is full for this book"  );
        }
    }

    @Test
    void reservation_created_whenSuccess_UT() {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        ReservationDto mockedReservationDto = getMockedReservationDto(1,false);

        User mockedUser = getMockedUser(1);
        when( mockedUserRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of(mockedUser) );

        Book mockedBook = getMockedBook(1);
        HashSet<Copy> copies = new HashSet<>();
        for(int i=0; i<4; i++) {
            Copy mockedCopy = mock(Copy.class);
            when(mockedCopy.isAvailable()).thenReturn(false);
            copies.add(mockedCopy);
        }

        when( mockedBook.getCopies() ).thenReturn( copies );
        when( mockedBookRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of( mockedBook ) );

        List<Reservation> bookReservations = new ArrayList<>();
        for(int i=0; i<2; i++){
            Reservation mockedReservation = getMockedReservation(i,false);
            when( mockedReservation.getUser() ).thenReturn( mock(User.class ) );
            bookReservations.add( mockedReservation );
        }
        when( mockedReservationRepository.findByBookId( any(Long.class ) ) ).thenReturn( bookReservations );


        Reservation mockedNewReservation = getMockedReservation(1,false);
        when( mockedNewReservation.getUser() ).thenReturn( mockedUser );

        List<Reservation> bookReservationsAfter = new ArrayList<>();
        for(int i=2; i<4; i++){
            Reservation mockedReservation = getMockedReservation(i,false);
            when( mockedReservation.getUser() ).thenReturn( mock(User.class ) );
            bookReservationsAfter.add( mockedReservation );
        }
        bookReservationsAfter.add( mockedNewReservation );
        when( mockedBook.getReservationList() ).thenReturn( bookReservationsAfter );

        when( mockedNewReservation.getBook() ).thenReturn( mockedBook );
        when( mockedNewReservation.getReservationStartDate() ).thenReturn( currentLocalDateTime );

        LocalDate expectedReturnDate = LocalDate.from(currentLocalDateTime.plusDays( 25L ));
        Instant instant= expectedReturnDate.atStartOfDay( ZoneId.systemDefault() ).toInstant();

        when( mockedReservationRepository.getMinExpectedReturnDate( any(Long.class ) ) ).thenReturn( Date.from( instant ) );

        when( mockedReservationRepository.save( any( Reservation.class ) ) ).thenReturn( mockedNewReservation );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            ReservationDto result = reservationServiceUnderTest.create( mockedReservationDto );
            assertThat( result ).isInstanceOf( ReservationDto.class );
            assertThat( result.getId() ).isEqualTo( mockedReservationDto.getId() );

            assertThat( result.getReservationStartDate() ).isInstanceOf( LocalDateTime.class );
            assertThat( result.getReservationStartDate() ).isEqualTo( currentLocalDateTime );

            assertThat( result.getMinExpectedReturnDate() ).isInstanceOf( Date.class );
            assertThat( result.getMinExpectedReturnDate() ).isEqualTo( Date.from( instant ) );

            assertThat( result.getNmbReservation() ).isInstanceOf( Integer.class );
            assertThat( result.getNmbReservation() ).isEqualTo( 3 );

            assertThat( result.getUserPosition() ).isInstanceOf( Integer.class );
            assertThat( result.getUserPosition() ).isEqualTo( 3 );

            assertThat( result.isActive() ).isInstanceOf( Boolean.class );
            assertThat( result.isActive() ).isEqualTo( false );

            assertThat( result.getUserDto() ).isInstanceOf( UserDto.class );
            assertThat( result.getUserDto().getUserId() ).isEqualTo( mockedReservationDto.getUserDto().getUserId() );
            assertThat( result.getBookDto() ).isInstanceOf( BookDto.class );
            assertThat( result.getBookDto().getBookId() ).isEqualTo( mockedReservationDto.getBookDto().getBookId() );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void reservation_getListByBookId_UT() {
        ArrayList<Reservation> rsvList = new ArrayList<>();
        when( mockedReservationRepository.findByBookId( any(Long.class ) ) ).thenReturn( rsvList );
        try{
            reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

            assertThat( reservationServiceUnderTest.getReservationsByBookId( 1L ) ).isInstanceOf( ArrayList.class );
            assertThat( reservationServiceUnderTest.getReservationsByBookId( 1L ).size() ).isEqualTo( 0 );

            rsvList.add( getMockedReservation(1, false));
            rsvList.add( getMockedReservation(2, false));
            rsvList.add( getMockedReservation(3, true));

            assertThat( reservationServiceUnderTest.getReservationsByBookId( 1L ) ).isInstanceOf( ArrayList.class );
            assertThat( reservationServiceUnderTest.getReservationsByBookId( 1L ).size() ).isEqualTo( 3 );

        }catch(Exception e ){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void reservation_getNextReservationBook_whenBookNotFound_UT() {
        when( mockedReservationRepository.findOlderByBookId( any(Long.class ) ) ).thenReturn( Optional.empty() );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.getNextReservationForBook( getMockedBookDto(1) );
            fail("NotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( NotFoundException.class );
            assertThat( e.getMessage() ).isEqualTo( "no reservation found"  );
        }
    }

    @Test
    void reservation_getNextReservationBook_whenSuccess_UT() {
        Reservation mockedReservation = getMockedReservation(1, false);

        when( mockedReservationRepository.findOlderByBookId( any(Long.class ) ) ).thenReturn( Optional.of( mockedReservation ) );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            ReservationDto  rsvResult = reservationServiceUnderTest.getNextReservationForBook( getMockedBookDto(1) );
            assertThat( rsvResult ).isInstanceOf( ReservationDto.class );
        }catch(Exception e ){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void reservation_active_whenReservationNotFound_UT() {
        ReservationDto mockedReservationDto =  getMockedReservationDto(1, false);

        when( mockedReservationRepository.findById( any(Long.class ) ) ).thenReturn( Optional.empty() );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.activeReservation( mockedReservationDto );
            fail("NotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( NotFoundException.class );
            assertThat( e.getMessage() ).isEqualTo( "reservation " + mockedReservationDto.getId() + " not found"   );
        }
    }

    @Test
    void reservation_active_whenSuccess_UT() {
        ReservationDto mockedReservationDto =  getMockedReservationDto(1, false);
        Reservation mockedReservation =  getMockedReservation(1, false);

        when( mockedReservationRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of( mockedReservation ) );


        Reservation mockedActiveReservation =  getMockedReservation(1, true);
        when( mockedReservationRepository.save( any(Reservation.class ) ) ).thenReturn( mockedActiveReservation );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            ReservationDto rsvResult = reservationServiceUnderTest.activeReservation( mockedReservationDto );
            assertThat( rsvResult.isActive() ).isInstanceOf( Boolean.class );
            assertThat( rsvResult.isActive() ).isEqualTo( true );
        }catch(Exception e ){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void reservation_findListReservationByUserId_whenNoResult_UT() {
        ArrayList<Reservation> rsvList = new ArrayList<>();

        when( mockedReservationRepository.findReservationByUserId( any(Long.class ) ) ).thenReturn( rsvList );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.findReservationByUserId( 1L );
            fail("ReservationNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( ReservationNotFoundException.class );
            assertThat( e.getMessage() ).isEqualTo( "No reservation pending" );
        }
    }

    @Test
    void reservation_findListReservationByUserId_whenResult_UT() {
        ArrayList<Reservation> rsvList = new ArrayList<>();
        rsvList.add( getMockedReservation(1, false));
        rsvList.add( getMockedReservation(2, false));
        rsvList.add( getMockedReservation(3, true));
        when( mockedReservationRepository.findReservationByUserId( any(Long.class ) ) ).thenReturn( rsvList );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            List<ReservationDto> listResult = reservationServiceUnderTest.findReservationByUserId( 1L );
            assertThat( listResult ).isInstanceOf( ArrayList.class );
            assertThat( listResult.size() ).isEqualTo( 3 );
        }catch(Exception e ){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void reservation_delete_whenNotFound_UT() {

        Reservation mockedReservation = getMockedReservation(1, false);

        when( mockedReservationRepository.findById( any(Long.class ) ) ).thenReturn( Optional.empty() );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.deleteById( mockedReservation.getId() );
            fail("ReservationNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf( ReservationNotFoundException.class );
            assertThat( e.getMessage() ).isEqualTo( "reservation " + mockedReservation.getId() + " not found "  );
        }
    }

    @Test
    void reservation_delete_whenSuccess_UT() {
        Reservation mockedReservation = getMockedReservation(1, false);

        when( mockedReservationRepository.findById( any(Long.class ) ) ).thenReturn( Optional.of( mockedReservation ) );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.deleteById( mockedReservation.getId() );
        }catch(Exception e ){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void reservation_haveAccess_WhenUnauthorized_UT() {
        //bad userId
        UserDto mockedUserDto = getMockedUserDto(5);
        when( mockedUserDto.getRoles() ).thenReturn( "CUSTOMER" );

        ReservationDto mockedReservationDto = getMockedReservationDto(1, false);

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.haveAccess( mockedUserDto, mockedReservationDto );
            fail("UnauthorizedException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(UnauthorizedException.class);
            assertThat( e.getMessage() ).isEqualTo( "access denied" );
        }
    }

    @Test
    void reservation_haveAccess_WhenOwnerSuccess_UT() {
        UserDto mockedUserDto = getMockedUserDto(1);
        when( mockedUserDto.getRoles() ).thenReturn( "CUSTOMER" );

        ReservationDto mockedReservationDto = getMockedReservationDto(1, false);

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.haveAccess( mockedUserDto, mockedReservationDto );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void reservation_haveAccess_WhenAdminSuccess_UT() {
        //Bad userId, but ADMIN
        UserDto mockedUserDto = getMockedUserDto(5);
        when( mockedUserDto.getRoles() ).thenReturn( "ADMIN" );

        ReservationDto mockedReservationDto = getMockedReservationDto(1, false);

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.haveAccess( mockedUserDto, mockedReservationDto );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void reservation_haveAccess_WhenEmployeeSuccess_UT() {
        //Bad userId, but EMPLOYEE
        UserDto mockedUserDto = getMockedUserDto(5);
        when( mockedUserDto.getRoles() ).thenReturn( "EMPLOYEE" );

        ReservationDto mockedReservationDto = getMockedReservationDto(1, false);

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.haveAccess( mockedUserDto, mockedReservationDto );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void reservation_isValid_UserRequired_UT() {
        ReservationDto mockedReservationDto = getMockedReservationDto(1, false);
        when( mockedReservationDto.getUserDto() ).thenReturn( null );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.isValid( mockedReservationDto );
            fail("UserNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(UserNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "user param is required" );
        }
    }

    @Test
    void reservation_isValid_BookRequired_UT() {
        ReservationDto mockedReservationDto = getMockedReservationDto(1, false);
        when( mockedReservationDto.getBookDto() ).thenReturn( null );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.isValid( mockedReservationDto );
            fail("BookNotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(BookNotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "book param is required" );
        }
    }

    @Test
    void reservation_getNmbReservationForBook_UT() {
        BookDto mockedBookDto = getMockedBookDto(1);
        List<ReservationDto> rsvList = new ArrayList<>();
        when( mockedBookDto.getReservations() ).thenReturn( rsvList );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            int result = reservationServiceUnderTest.getNmbReservationForBook( mockedBookDto );
            assertThat( result ).isInstanceOf( Integer.class);
            assertThat( result ).isEqualTo( 0 );

            rsvList.add( getMockedReservationDto(1, true ) );
            rsvList.add( getMockedReservationDto(4, false ) );
            rsvList.add( getMockedReservationDto(5, false ) );

            int result2 = reservationServiceUnderTest.getNmbReservationForBook( mockedBookDto );
            assertThat( result2 ).isInstanceOf( Integer.class);
            assertThat( result2 ).isEqualTo( 3 );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }

    @Test
    void reservation_haveActiveReservationForUser_whenNotFoundResult_UT() {
        UserDto mockedUserDto = getMockedUserDto(1);
        BookDto mockedBookDto = getMockedBookDto(1);

        when( mockedReservationRepository.findActiveReservationForUserByBookId( any( Long.class ), any( Long.class ) ) ).thenReturn( Optional.empty() );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            reservationServiceUnderTest.haveActiveReservationForUser( mockedUserDto, mockedBookDto );
            fail("NotFoundException expected but not thrown");
        }catch( Exception e ){
            assertThat( e ).isInstanceOf(NotFoundException.class);
            assertThat( e.getMessage() ).isEqualTo( "active reservation not found" );
        }
    }

    @Test
    void reservation_haveActiveReservationForUser_whenResultFound_UT() {
        UserDto mockedUserDto = getMockedUserDto(1);
        BookDto mockedBookDto = getMockedBookDto(1);

        Reservation mockedReservation = getMockedReservation( 1, true);

        when( mockedReservationRepository.findActiveReservationForUserByBookId( any( Long.class ), any( Long.class ) ) ).thenReturn( Optional.of( mockedReservation ) );

        reservationServiceUnderTest = new ReservationServiceImpl( mockedReservationRepository, mockedUserRepository, mockedBookRepository );

        try{
            ReservationDto result = reservationServiceUnderTest.haveActiveReservationForUser( mockedUserDto, mockedBookDto );
            assertThat( result.isActive() ).isInstanceOf( Boolean.class );
            assertThat( result.isActive() ).isEqualTo( true );
            assertThat( result.getUserDto().getUserId() ).isInstanceOf( Long.class );
            assertThat( result.getUserDto().getUserId() ).isEqualTo( 1 );
        }catch(Exception e){
            fail("Unexpected Exception: ", e);
        }
    }
}
