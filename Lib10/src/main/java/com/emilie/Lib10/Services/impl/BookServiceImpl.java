package com.emilie.Lib10.Services.impl;


import com.emilie.Lib10.Exceptions.*;
import com.emilie.Lib10.Repositories.AuthorsRepository;
import com.emilie.Lib10.Repositories.BookRepository;
import com.emilie.Lib10.Services.contract.BookService;
import com.emilie.Lib10.Models.Dtos.*;
import com.emilie.Lib10.Models.Entities.*;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorsRepository authorsRepository;


    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           AuthorsRepository authorsRepository) {
        this.bookRepository=bookRepository;
        this.authorsRepository=authorsRepository;
    }


    @Override
    public List<BookDto> findAll() {
        List<Book> books=bookRepository.findAll();
        List<BookDto> bookDtos=new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto=bookToBookDto( book );
            bookDtos.add( bookDto );
        }
        return bookDtos;
    }

    @Override
    public List<BookDto> searchBooks(Long libraryId, String title, String isbn, String firstName, String lastName) {

        List<Book> books;
        if (libraryId != null) {
            books=bookRepository.searchBooksByLibrary( libraryId, title, isbn, firstName, lastName );
        } else {
            books=bookRepository.searchBooks( title, isbn, firstName, lastName );
        }
        List<BookDto> bookDtos=new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto=bookToBookDto( book );

            if (libraryId != null) { //filtre copy by libId si libId n'est pas null
                Set<CopyDto> copyDtosList=new HashSet<>();
                if (bookDto.getCopyDtos() != null) {
                    for (CopyDto copyDto : bookDto.getCopyDtos()) {
                        if (copyDto.getLibraryDto().getLibraryId().equals( libraryId )) {
                            copyDtosList.add( copyDto );
                        }
                    }
                    bookDto.setCopyDtos( copyDtosList );
                }
            }

            bookDtos.add( bookDto );
        }
        return bookDtos;
    }


    @Override
    public BookDto findById(Long id) throws BookNotFoundException {
        Optional<Book> optionalBook=bookRepository.findById( id );
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException( "Book " + id + " not found" );
        }
        Book book=optionalBook.get();
        return bookToBookDto( book );
    }

    @Override
    public BookDto save(BookDto bookDto)
            throws BookAlreadyExistException, AuthorNotFoundException {
        Optional<Book> optionalBook=bookRepository.findByTitle( bookDto.getTitle() );
        if (optionalBook.isPresent()) {
            throw new BookAlreadyExistException( "book " + bookDto.getTitle() + " already exists" );
        }
        if(bookDto.getAuthorDto() == null){
            throw new AuthorNotFoundException( "author is required" );
        }
        Optional<Author> optionalAuthor=authorsRepository.findById( bookDto.getAuthorDto().getAuthorId() );
        if (!optionalAuthor.isPresent()) {
            throw new AuthorNotFoundException( "author " + bookDto.getAuthorDto().getAuthorId() + " not found" );
        }
        Book book=bookDtoToBook( bookDto );
        book.setAuthor( optionalAuthor.get() );
        book=bookRepository.save( book );
        return bookToBookDto( book );
    }


    @Override
    public BookDto update(BookDto bookDto)
            throws BookNotFoundException {
        Optional<Book> optionalBook=bookRepository.findById( bookDto.getBookId() );
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException( "book " + bookDto.getBookId() + " not found" );
        }

        Book book=optionalBook.get();
        book.setTitle( bookDto.getTitle() );
        book.setIsbn( bookDto.getIsbn() );
        book.setSummary( bookDto.getSummary() );
        book=bookRepository.save( book );
        return bookToBookDto( book );
    }


    @Override
    public boolean deleteById(Long id)
            throws BookNotFoundException, ImpossibleDeleteBookException {
        Optional<Book> optionalBook=bookRepository.findById( id );
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException( "book " + id + " not found" );
        } else {
            Book book=optionalBook.get();

            if (book.getCopies().size() > 0) {
                System.out.println( book );
                throw new ImpossibleDeleteBookException( "This book " + id + " have existing copy" );
            }
            try {

                bookRepository.deleteById( id );
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }


    @Override
    public BookDto findByTitle(String title) throws BookNotFoundException {
        Optional<Book> optionalBook=bookRepository.findByTitle( title );
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException( "books " + title + " not found" );
        }
        Book book=optionalBook.get();
        BookDto bookDto=this.bookToBookDto( book );
        return bookDto;
    }

    /**
     * check if one copy of the book is Available.
     * @param book
     * @return
     */
    @Override
    public boolean bookIsAvailable(Book book) {
        boolean result = false;

            Iterator<Copy> i = book.getCopies().iterator();
            while (i.hasNext() && !result){
                if(i.next().isAvailable()){ result = true; }
            }

        return result;
    }

    @Override
    public void isNewBookValid(BookDto bookDto) {
        Optional<Book> optionalBook=bookRepository.findByTitle( bookDto.getTitle() );
        if(bookDto.getAuthorDto() == null){
            throw new AuthorNotFoundException( "author " + bookDto.getAuthorDto().getAuthorId() + " not found" );
        }
        if (optionalBook.isPresent()) {
            throw new BookAlreadyExistException( "book " + bookDto.getTitle() + " already exists" );
        }
        Optional<Author> optionalAuthor=authorsRepository.findById( bookDto.getAuthorDto().getAuthorId() );
        if (!optionalAuthor.isPresent()) {
            throw new AuthorNotFoundException( "author " + bookDto.getAuthorDto().getAuthorId() + " not found" );
        }
    }

    public int getMaxReservationForBook(Book book){
        return book.getCopies().size()*2;
    }

    private BookDto bookToBookDto(Book book) {
        BookDto bookDto=new BookDto();
        bookDto.setBookId( book.getBookId() );
        bookDto.setTitle( book.getTitle() );
        bookDto.setIsbn( book.getIsbn() );
        bookDto.setSummary( book.getSummary() );

        Set<CopyDto> copyDtos=new HashSet<>();
        if (book.getCopies() != null) {
            for (Copy copy : book.getCopies()) {
                CopyDto copyDto=makeCopyDto( copy );

                copyDtos.add( copyDto );
            }

            bookDto.setCopyDtos( copyDtos );


        }
        bookDto.setAuthorDto( makeAuthorDto( book.getAuthor() ) );

        List<ReservationDto> reservationDtoList = new ArrayList<>();
        if(book.getReservationList() != null){
            for(Reservation reservation : book.getReservationList() ){
                reservationDtoList.add(makeReservationsDto(reservation));
            }
        }
        bookDto.setReservations( reservationDtoList );

        bookDto.setAvailable( this.bookIsAvailable( book ) );
        bookDto.setMaxReservation( this.getMaxReservationForBook( book ) );

        return bookDto;
    }
    private Book bookDtoToBook(BookDto bookDto) {
        Book book=new Book();
        book.setBookId( bookDto.getBookId() );
        book.setTitle( bookDto.getTitle() );
        book.setIsbn( bookDto.getIsbn() );
        book.setSummary( bookDto.getSummary() );

        Set<Copy> copies=new HashSet<>();
        if (bookDto.getCopyDtos() != null) {
            for (CopyDto copyDto : bookDto.getCopyDtos()) {
                Copy copy=makeCopy( copyDto );
                copies.add( copy );
            }
            book.setCopies( copies );

        }

        book.setAuthor( makeAuthor( bookDto.getAuthorDto() ) );

        return book;

    }


    private CopyDto makeCopyDto(Copy copy) {
        CopyDto copyDto=new CopyDto();
        copyDto.setId( copy.getId() );
        copyDto.setAvailable( copy.isAvailable() );
        copyDto.setLibraryDto( makeLibraryDto( copy.getLibrary() ) );
        return copyDto;
    }

    private Copy makeCopy(CopyDto copyDto) {
        Copy copy=new Copy();
        copy.setId( copyDto.getId() );
        copy.setAvailable( copyDto.isAvailable() );
        copy.setLibrary( makeLibrary( copyDto.getLibraryDto() ) );
        return copy;
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

    private ReservationDto makeReservationsDto(Reservation reservation){

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId( reservation.getId() );

        UserDto userDto = new UserDto();
        userDto.setUserId( reservation.getUser().getId());
        reservationDto.setUserDto( userDto );

        BookDto bookDto = new BookDto();
        bookDto.setBookId( reservation.getBook().getBookId() );
        reservationDto.setBookDto( bookDto );

        reservationDto.setActive( reservation.isActive() );
        reservationDto.setReservationEndDate( reservation.getReservationEndDate() );
        reservationDto.setReservationStartDate( reservation.getReservationStartDate() );

        reservationDto.setUserPosition( getUserPosition( reservation ) );

        return reservationDto;
    }

    public int getUserPosition(Reservation reservation){

        int res = 0;
        for(Reservation checkedReservation : reservation.getBook().getReservationList()){
            if(checkedReservation.getId().equals( reservation.getId() )){
                res = reservation.getBook().getReservationList().indexOf( checkedReservation );
            }
        }
        return res+1;
    }

}