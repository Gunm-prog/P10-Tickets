package com.emilie.Lib10.Services.impl;

import com.emilie.Lib10.Exceptions.*;
import com.emilie.Lib10.Models.Dtos.*;
import com.emilie.Lib10.Models.Entities.*;
import com.emilie.Lib10.Models.Entities.Book;
import com.emilie.Lib10.Models.Entities.Copy;
import com.emilie.Lib10.Models.Entities.Reservation;
import com.emilie.Lib10.Models.Entities.User;
import com.emilie.Lib10.Repositories.BookRepository;
import com.emilie.Lib10.Repositories.CopyRepository;
import com.emilie.Lib10.Repositories.ReservationRepository;
import com.emilie.Lib10.Repositories.UserRepository;
import com.emilie.Lib10.Services.contract.ReservationService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, BookRepository bookRepository, CopyRepository copyRepository) {
        this.reservationRepository=reservationRepository;
        this.userRepository=userRepository;
        this.bookRepository = bookRepository;
    }


   /* @Override
    public ReservationDto (ReservationDto reservationDto) createReservation(){}*/

    @Override
    public List<ReservationDto> findAll() {
        List<Reservation> reservations=reservationRepository.findAll();
        List<ReservationDto> reservationDtos=new ArrayList<>();
        for (Reservation reservation : reservations) {
            ReservationDto reservationDto=reservationToReservationDto( reservation );
            reservationDtos.add( reservationDto );
        }
        return reservationDtos;
    }

    @Override
    public ReservationDto findById(Long id) throws ReservationNotFoundException {
        Optional<Reservation> optionalReservation=reservationRepository.findById( id );
        if (!optionalReservation.isPresent()) {
            throw new ReservationNotFoundException( "Reservation not found" );
        }
        Reservation reservation=optionalReservation.get();
        return reservationToReservationDto( reservation );
    }


    /*
    TODO recupere le nb de copies qui existent pour ce livre (bookId)
        cette valeur sera à multiplier par 2 (nb copies à multiplier par 2) cf maxResa(nb max de resa autorisées)
                 if  il faudra autoriser la création d'une réservation uniquement si maxResa < nb reservations de la liste récupérée
        l.105
                else  si la resa est impossible, throw renvoyer une exception la liste d'attente est complète!
        si on peut faire la resa (donc création resa dans le if)
        on va instancier une nouvelle instance de la classe resa à qui on va donner l'user qui est dans le resaDto qu'on a reçu
        on va aussi lui donner le book qui se trouve dans le resaDto qu'on a reçu
        Et en plus on va lui donner la date du jour courante
        On peut donc persister la resa en utilisant resa.repository.save
        methode doit return une resaDto

        todo: une reservation ne peut être faite que si le book est indisponible.
        todo: un user ne peut pas reserver un book qu'il a déjà emprunté.
    */

    /**
     * @return ReservationDto
     * @throws UserNotFoundException, BookNotFoundException, ReservationAlreadyExistException, UnauthorizedException
     */
    @Override
    public ReservationDto create(ReservationDto reservationDto)
            throws UserNotFoundException,
            BookNotFoundException,
            ReservationAlreadyExistException {

        Optional<User> optionalUser=userRepository.findById( reservationDto.getUserDto().getUserId() );
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException( "user " + reservationDto.getUserDto().getUserId() + " not found" );
        }

        Optional<Book> optionalBook=bookRepository.findById( reservationDto.getBookDto().getBookId());
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException( "book " + reservationDto.getBookDto().getBookId() + " not found" );
        }

        User user = optionalUser.get();
        Book book = optionalBook.get();

        //check if the book is already loaned and not returned by this user
        for( Loan loan : user.getLoans()) {
            if( !loan.isReturned() && loan.getCopy().getBook() == book){
                throw new UnauthorizedException( "this book is already loaned by this user" );
            }
        }

        //check if the book have a copy available and throw exception if it's found.
        for( Copy copy : book.getCopies() ){
            if (copy.isAvailable()) {
                throw new UnauthorizedException( "a copy for this book is available, reservation isn't enabled" );
            }
        }

        //get Reservation List for a specific book
        List<Reservation> reservations=reservationRepository.findByBookId( reservationDto.getBookDto().getBookId() );

        //check if the user have already reserved this book
        for( Reservation reservation : reservations ){
            if(reservation.getUser() == user){
                throw new ReservationAlreadyExistException("the book is already reserved by this user");
            }
        }

        //  reservations.get( nbOfCopiesByBook );
        int maxResa= book.getCopies().size() * 2;
        if ( reservations.size() < maxResa ) {
            Reservation reservation = new Reservation();
       //     Reservation reservation = reservationDtoToReservation( reservationDto );
            reservation.setUser( user );
            reservation.setBook( book );
            reservation.setReservationStartDate( LocalDateTime.now() );
            reservationRepository.save( reservation );

          //  return reservation;
            return reservationToReservationDto( reservation );

        } else {
            throw new MaxResaAtteintException( "reservation list is full for this book" );
        }
    }

    public List<ReservationDto> getReservationsByBookId( Long bookId ){
        List<Reservation> reservationsList = reservationRepository.findByBookId( bookId );

        List<ReservationDto> reservationDtos = new ArrayList<>();
        for ( Reservation reservation : reservationsList ){
            reservationDtos.add(reservationToReservationDto(reservation));
        }

        return reservationDtos;
    }

    @Override
    public ReservationDto getNextReservationForBook(BookDto bookDto) throws NotFoundException {
        Optional <Reservation> optionalReservation = reservationRepository.findOlderByBookId( bookDto.getBookId() );

        if(!optionalReservation.isPresent()){
            throw new NotFoundException( "no reservation found" );
        }

        return reservationToReservationDto( optionalReservation.get() );
    }

    @Override
    public ReservationDto activeReservation(ReservationDto reservationDto) throws NotFoundException {
        Optional <Reservation> optionalReservation = reservationRepository.findById(reservationDto.getId());
        if (!optionalReservation.isPresent()) {
            throw new NotFoundException( "reservation " + reservationDto.getId() + " not found" );
        }

        Reservation reservation = optionalReservation.get();

        reservation.setReservationEndDate( makePeriodLocalDateTime( 2, null) );
        reservation.setActive( true );
        reservationRepository.save(reservation);
        return reservationToReservationDto( reservation );
    }

    @Override
    public List<ReservationDto> findReservationByUserId(Long userId) throws ReservationNotFoundException{

        List<Reservation> reservations=reservationRepository.findReservationByUserId( userId  );
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException( "No reservation pending" );
        }
        List<ReservationDto> reservationDtos=new ArrayList<>();
        for (Reservation reservation : reservations) {
            ReservationDto reservationDto=reservationToReservationDto( reservation );
            reservationDtos.add( reservationDto );
        }
        return reservationDtos;
    }

    @Override
    public void deleteById(Long id) throws ReservationNotFoundException, ImpossibleDeleteReservationException {
        Optional<Reservation> optionalReservation=reservationRepository.findById( id );
        if (!optionalReservation.isPresent()) {
            throw new ReservationNotFoundException( "reservation " + id + " not found " );
       /* } else if (!optionalReservation.get().isFinished()) {
            throw new ImpossibleDeleteReservationException( "reservation " + id + " is out of reservation list" );*/
        }

     //   if(reservation.getUser() !=)
            reservationRepository.deleteById( id );
    }

    @Override
    public void haveAccess(UserDto loggedUser, ReservationDto reservationDto){
        //if loggedUser is a customer
        if(loggedUser.getRoles() == "CUSTOMER"){
            //check if the reservation is owned by loggedUser
            if(!Objects.equals( loggedUser.getUserId(), reservationDto.getUserDto().getUserId() )){
                throw new UnauthorizedException( "access denied" );
            }
        }
    }

    @Override
    public ReservationDto haveActiveReservationForUser(UserDto userDto, BookDto bookDto) throws NotFoundException {
        Optional<Reservation> OptionalReservation = reservationRepository.findActiveReservationForUserByBookId( userDto.getUserId(), bookDto.getBookId() );
        if(!OptionalReservation.isPresent()){
            throw new NotFoundException( "active reservation not found" );
        }

        return reservationToReservationDto( OptionalReservation.get() );
    }

    private ReservationDto reservationToReservationDto (Reservation reservation){
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId( reservation.getId() );
        reservationDto.setReservationStartDate( reservation.getReservationStartDate() );
        reservationDto.setReservationEndDate( reservation.getReservationEndDate() );
        reservationDto.setActive(reservation.isActive());

        User user = reservation.getUser();
        UserDto userDto = new UserDto();
        userDto.setUserId( user.getId() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );
        userDto.setEmail( user.getEmail() );
        reservationDto.setUserDto( userDto );

        reservationDto.setBookDto( makeBookDto(reservation.getBook()) );

        return reservationDto;
    }

    private Reservation reservationDtoToReservation (ReservationDto reservationDto){

        System.out.println( reservationDto );

        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setReservationStartDate( reservationDto.getReservationStartDate() );
        reservation.setReservationEndDate( reservationDto.getReservationEndDate() );
        reservation.setActive(reservationDto.isActive());

        User user = new User();
        user.setId( reservationDto.getUserDto().getUserId() );
        user.setFirstName( reservationDto.getUserDto().getFirstName());
        user.setLastName( reservationDto.getUserDto().getLastName() );
        user.setEmail( reservationDto.getUserDto().getEmail() );
        reservation.setUser( user );

        reservation.setBook( makeBook(reservationDto.getBookDto()) );

        return reservation;
    }


    private UserDto userToUserDto(User user) {
        UserDto userDto=new UserDto();
        userDto.setUserId( user.getId() );
        userDto.setEmail( user.getEmail() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );
        return userDto;
    }


    private User userDtoToUser(UserDto userDto) {
        User user=new User();
        user.setId( userDto.getUserId() );

        user.setEmail( userDto.getEmail() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );
        return user;
    }

    private CopyDto makeCopyDto(Copy copy){
        CopyDto copyDto = new CopyDto();
        copyDto.setId(copy.getId());
        copyDto.setAvailable( copy.isAvailable() );
        copyDto.setLibraryDto( makeLibraryDto(copy.getLibrary()) );
        copyDto.setBookDto( makeBookDto(copy.getBook()) );

        return copyDto;
    }

    private Copy makeCopy(CopyDto copyDto){
        Copy copy = new Copy();
        copy.setId(copyDto.getId());
        copy.setAvailable( copyDto.isAvailable() );
        copy.setLibrary( makeLibrary(copyDto.getLibraryDto()));
        copy.setBook( makeBook(copyDto.getBookDto()) );

        return copy;
    }

    private BookDto makeBookDto(Book book){
        BookDto bookDto = new BookDto();
        bookDto.setBookId( book.getBookId() );
        bookDto.setTitle( book.getTitle() );
        bookDto.setIsbn( book.getIsbn() );
        bookDto.setSummary( book.getSummary() );
        bookDto.setAuthorDto(makeAuthorDto(book.getAuthor()));

        return bookDto;
    }

    private Book makeBook(BookDto bookDto) {
        Book book=new Book();
        book.setBookId( bookDto.getBookId() );
        book.setTitle( bookDto.getTitle() );
        book.setIsbn( bookDto.getIsbn() );
        book.setSummary( bookDto.getSummary() );
        book.setAuthor( makeAuthor( bookDto.getAuthorDto() ) );
        return book;
    }


    private AuthorDto makeAuthorDto(Author author) {
        AuthorDto authorDto=new AuthorDto();
        authorDto.setAuthorId( author.getAuthorId() );
        authorDto.setFirstName( author.getFirstName() );
        authorDto.setLastName( author.getLastName() );
        return authorDto;
    }

    private Author makeAuthor(AuthorDto authorDto) {
        Author author=new Author();
        author.setAuthorId( authorDto.getAuthorId() );
        author.setFirstName( authorDto.getFirstName() );
        author.setLastName( authorDto.getLastName() );
        return author;
    }


    private LibraryDto makeLibraryDto(Library library) {
        LibraryDto libraryDto=new LibraryDto();
        libraryDto.setLibraryId( library.getLibraryId() );
        libraryDto.setName( library.getName() );
        libraryDto.setAddressDto( makeAddressDto( library.getAddress() ) );
        library.setPhoneNumber( library.getPhoneNumber() );
        return libraryDto;
    }

    private Library makeLibrary(LibraryDto libraryDto) {
        Library library=new Library();
        library.setLibraryId( libraryDto.getLibraryId() );
        library.setName( libraryDto.getName() );
        library.setAddress( makeAddress( libraryDto.getAddressDto() ) );
        library.setPhoneNumber( libraryDto.getPhoneNumber() );
        return library;
    }


    private AddressDto makeAddressDto(Address address) {
        AddressDto addressDto=new AddressDto();
        addressDto.setNumber( address.getNumber() );
        addressDto.setStreet( address.getStreet() );
        addressDto.setZipCode( address.getZipCode() );
        addressDto.setCity( address.getCity() );
        return addressDto;
    }

    private Address makeAddress(AddressDto addressDto) {
        Address address=new Address();
        address.setNumber( addressDto.getNumber() );
        address.setStreet( addressDto.getStreet() );
        address.setZipCode( addressDto.getZipCode() );
        address.setCity( addressDto.getCity() );
        return address;
    }


    private LocalDateTime makePeriodLocalDateTime(int numberOfDays, Date initialEndDate) {

        LocalDateTime localDateTime=LocalDateTime.now();

        if (initialEndDate != null) {
            localDateTime = initialEndDate.toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime();
        }

        localDateTime=localDateTime.plusDays( numberOfDays );
     //   Instant instant=localDate.atStartOfDay( ZoneId.systemDefault() ).toInstant();
     //   Date date=Date.from( instant );
        return localDateTime;
    }

}
