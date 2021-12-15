package com.emilie.Lib7.Services.impl;


import com.emilie.Lib7.Exceptions.AddressNotFoundException;
import com.emilie.Lib7.Exceptions.ImpossibleDeleteUserException;
import com.emilie.Lib7.Exceptions.UserAlreadyExistException;
import com.emilie.Lib7.Exceptions.UserNotFoundException;
import com.emilie.Lib7.Models.Dtos.*;
import com.emilie.Lib7.Models.Entities.*;
import com.emilie.Lib7.Repositories.UserRepository;
import com.emilie.Lib7.Services.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository=userRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }


    @Override
    public UserDto getLoggedUser() {
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto=findByEmail( email );
        return userDto;
    }


    @Override
    public UserDto findById(Long id) throws UserNotFoundException {
        Optional<User> optionalUser=userRepository.findById( id );
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException( "User not found" );
        }
        User user=optionalUser.get();
        return userToUserDto( user );
    }

    @Override
    public UserDto save(UserDto userDto) throws UserAlreadyExistException, AddressNotFoundException {
        isNewUserValid( userDto );

        System.out.println( userDto );
        User user=userDtoToUser( userDto );
        user=userRepository.save( user );

        return userToUserDto( user );
    }

    @Override
    public void isNewUserValid(UserDto userDto) {
        Optional<User> optionalUser=userRepository.findByEmail( userDto.getEmail() );
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistException( "Email already exists" );
        }
        if (userDto.getAddressDto() == null) {
            throw new AddressNotFoundException( "Address not found" );
        }
    }


    @Override
    public UserDto update(UserDto userDto) throws UserNotFoundException {
        Optional<User> optionalUser=userRepository.findById( userDto.getUserId() );
        if (userDto.getAddressDto() == null) {
            throw new AddressNotFoundException( "Address not found" );
        }
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException( "User not found" );
        }

        User user=optionalUser.get();


        if (userDto.getEmail() != null) user.setEmail( userDto.getEmail() );
        if (userDto.getFirstName() != null) user.setFirstName( userDto.getFirstName() );
        if (userDto.getLastName() != null) user.setLastName( userDto.getLastName() );
        if (userDto.getAddressDto() != null) user.setAddress( makeAddress( userDto.getAddressDto() ) );

        user=userRepository.save( user );
        return userToUserDto( user );
    }


    @Override
    public boolean deleteById(Long id)
            throws UserNotFoundException, ImpossibleDeleteUserException {
        Optional<User> optionalUser=userRepository.findById( id );
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException( "user not found" );
        } else if (optionalUser.get().getLoans().size() > 0) {
            throw new ImpossibleDeleteUserException( "This user " + id + " have existing loans" );
        }
        try {
            userRepository.deleteById( id );
        } catch (Exception e) {
            return false;
        }

        return true;
    }


    @Override
    public UserDto findByEmail(String email) throws UserNotFoundException {
        Optional<User> optionalUser=userRepository.findByEmail( email );
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException( "User " + email + " not found" );
        }
        User user=optionalUser.get();
        UserDto userDto=this.userToUserDto( user );
        return userDto;
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users=userRepository.findAll();
        List<UserDto> userDtos=new ArrayList<>();
        for (User user : users) {
            UserDto userDto=userToUserDto( user );
            userDto.setPassword( "" );
            userDtos.add( userDto );
        }
        return userDtos;
    }


    private UserDto userToUserDto(User user) {
        UserDto userDto=new UserDto();
        userDto.setUserId( user.getId() );
        userDto.setPassword( user.getPassword() );
        userDto.setRoles( user.getRoles() );
        userDto.setActive( user.isActive() );
        userDto.setEmail( user.getEmail() );
        userDto.setLastName( user.getLastName() );
        userDto.setFirstName( user.getFirstName() );

        Set<LoanDto> loanDtos=new HashSet<>();
        if (user.getLoans() != null) {
            for (Loan loan : user.getLoans()) {
                LoanDto loanDto=makeLoanDto( loan );
                loanDtos.add( loanDto );
            }
            userDto.setLoanDtos( loanDtos );

        }

        AddressDto addressDto=makeAddressDto( user.getAddress() );

        userDto.setAddressDto( addressDto );


        return userDto;
    }


    private User userDtoToUser(UserDto userDto) {
        User user=new User();
        user.setId( userDto.getUserId() );
        user.setPassword( userDto.getPassword() );
        user.setEmail( userDto.getEmail() );
        user.setRoles( userDto.getRoles() );
        user.setActive( userDto.isActive() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );

        Set<Loan> loans=new HashSet<>();
        if (userDto.getLoanDtos() != null) {
            for (LoanDto loanDto : userDto.getLoanDtos()) {
                Loan loan=makeLoan( loanDto );
                loans.add( loan );
            }
            user.setLoans( loans );
        }

        Address address=makeAddress( userDto.getAddressDto() );
        user.setAddress( address );

        return user;
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

    private LoanDto makeLoanDto(Loan loan) {
        LoanDto loanDto=new LoanDto();
        loanDto.setId( loan.getId() );
        loanDto.setLoanStartDate( loan.getLoanStartDate() );
        loanDto.setLoanEndDate( loan.getLoanEndDate() );
        loanDto.setExtended( loan.isExtended() );
        loanDto.setReturned( loan.isReturned() );
        loanDto.setCopyDto( makeCopyDto( loan.getCopy() ) );
        return loanDto;
    }

    private Loan makeLoan(LoanDto loanDto) {

        Loan loan=new Loan();
        loan.setId( loanDto.getId() );
        loan.setLoanStartDate( loanDto.getLoanStartDate() );
        loan.setLoanEndDate( loanDto.getLoanEndDate() );
        loan.setExtended( loanDto.isExtended() );
        loan.setReturned( loanDto.isReturned() );
        loan.setCopy( makeCopy( loanDto.getCopyDto() ) );
        return loan;
    }

    private CopyDto makeCopyDto(Copy copy) {
        CopyDto copyDto=new CopyDto();
        copyDto.setId( copy.getId() );
        copyDto.setAvailable( copy.isAvailable() );
        copyDto.setBookDto( makeBookDto( copy.getBook() ) );
        copyDto.setLibraryDto( makeLibraryDto( copy.getLibrary() ) );
        return copyDto;
    }

    private Copy makeCopy(CopyDto copyDto) {
        Copy copy=new Copy();
        copy.setId( copyDto.getId() );
        copy.setAvailable( copyDto.isAvailable() );
        copy.setBook( makeBook( copyDto.getBookDto() ) );
        copy.setLibrary( makeLibrary( copyDto.getLibraryDto() ) );
        return copy;
    }

    private BookDto makeBookDto(Book book) {
        BookDto bookDto=new BookDto();
        bookDto.setBookId( book.getBookId() );
        bookDto.setTitle( book.getTitle() );
        bookDto.setIsbn( book.getIsbn() );
        bookDto.setAuthorDto( makeAuthorDto( book.getAuthor() ) );
        return bookDto;
    }

    private Book makeBook(BookDto bookDto) {
        Book book=new Book();
        book.setBookId( bookDto.getBookId() );
        book.setTitle( bookDto.getTitle() );
        book.setIsbn( bookDto.getIsbn() );
        book.setAuthor( makeAuthor( bookDto.getAuthorDto() ) );
        return book;
    }

    private LibraryDto makeLibraryDto(Library library) {
        LibraryDto libraryDto=new LibraryDto();
        libraryDto.setLibraryId( library.getLibraryId() );
        libraryDto.setName( library.getName() );
        return libraryDto;
    }

    private Library makeLibrary(LibraryDto libraryDto) {
        Library library=new Library();
        library.setLibraryId( libraryDto.getLibraryId() );
        library.setName( libraryDto.getName() );
        library.setPhoneNumber( libraryDto.getPhoneNumber() );
        library.setAddress( makeAddress( libraryDto.getAddressDto() ) );
        return library;
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


}