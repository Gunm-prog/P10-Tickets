package com.emilie.Lib7.Services.impl;

import com.emilie.Lib7.Exceptions.*;
import com.emilie.Lib7.Models.Dtos.*;
import com.emilie.Lib7.Models.Entities.*;
import com.emilie.Lib7.Repositories.CopyRepository;
import com.emilie.Lib7.Repositories.LoanRepository;
import com.emilie.Lib7.Repositories.UserRepository;
import com.emilie.Lib7.Services.contract.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final CopyRepository copyRepository;


    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository,
                           UserRepository userRepository,
                           CopyRepository copyRepository) {

        this.loanRepository=loanRepository;
        this.userRepository=userRepository;
        this.copyRepository=copyRepository;
    }


    @Override
    public List<LoanDto> findAll() {
        List<Loan> loans=loanRepository.findAll();
        List<LoanDto> loanDtos=new ArrayList<>();
        for (Loan loan : loans) {
            LoanDto loanDto=loanToLoanDto( loan );
            loanDtos.add( loanDto );
        }
        return loanDtos;
    }


    @Override
    public LoanDto findById(Long id) throws LoanNotFoundException {
        Optional<Loan> optionalLoan=loanRepository.findById( id );
        if (!optionalLoan.isPresent()) {
            throw new LoanNotFoundException( "Loan not found" );
        }
        Loan loan=optionalLoan.get();
        return loanToLoanDto( loan );
    }

    @Override
    public LoanDto save(LoanDto loanDto)
            throws UserNotFoundException, CopyNotFoundException, LoanAlreadyExistsException {

        Optional<User> optionalUser=userRepository.findById( loanDto.getUserDto().getUserId() );
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException( "user " + loanDto.getUserDto().getUserId() + " not found" );
        }

        Optional<Copy> optionalCopy=copyRepository.findById( loanDto.getCopyDto().getId() );
        if (!optionalCopy.isPresent()) {
            throw new CopyNotFoundException( "copy " + loanDto.getCopyDto().getId() + " not found" );
        }

        Optional<Loan> optionalLoan=loanRepository.findByCopyId( loanDto.getCopyDto().getId() );
        if (optionalLoan.isPresent()) {
            throw new LoanAlreadyExistsException( "loan for copy " + loanDto.getCopyDto().getId() + " already exists" );
        }

        UserDto userDto=userToUserDto( optionalUser.get() );
        loanDto.setUserDto( userDto );
        CopyDto copyDto=makeCopyDto( optionalCopy.get() );
        loanDto.setCopyDto( copyDto );

        Loan loan=loanDtoToLoan( loanDto );
        loan.setLoanStartDate( makePeriodDate( 0, null ) );
        loan.setLoanEndDate( makePeriodDate( 30, null ) );
        loan.setExtended( false );
        loan=loanRepository.save( loan );
        loan.getCopy().setAvailable( false );
        copyRepository.save( loan.getCopy() );
        return loanToLoanDto( loan );
    }


    @Override
    public List<LoanDto> findLoansByUserId(Long userId) throws LoanNotFoundException {
        List<Loan> loans=loanRepository.findLoansByUserId( userId );
        if (loans.isEmpty()) {
            throw new LoanNotFoundException( "Loan not found" );
        }
        List<LoanDto> loanDtos=new ArrayList<>();
        for (Loan loan : loans) {
            LoanDto loanDto=loanToLoanDto( loan );
            loanDtos.add( loanDto );
        }
        return loanDtos;
    }

    @Override
    public LoanDto update(LoanDto loanDto) throws LoanNotFoundException, UserNotFoundException, CopyNotFoundException {
        Optional<Loan> optionalLoan=loanRepository.findById( loanDto.getId() );
        if (!optionalLoan.isPresent()) {
            throw new LoanNotFoundException( "loan not found" );
        }

        Optional<User> optionalUser=userRepository.findById( loanDto.getUserDto().getUserId() );
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException( "user not found" );
        }

        Optional<Copy> optionalCopy=copyRepository.findById( loanDto.getCopyDto().getId() );
        if (!optionalCopy.isPresent()) {
            throw new CopyNotFoundException( "copy not found" );
        }

        Loan loan=loanDtoToLoan( loanDto );
        loan.setUser( optionalUser.get() );
        loan.setCopy( optionalCopy.get() );
        loan=loanRepository.save( loan );
        return loanToLoanDto( loan );
    }

    @Override
    public LoanDto extendLoan(Long loanId) throws LoanNotFoundException, ImpossibleExtendLoanException {
        Optional<Loan> optionalLoan=loanRepository.findById( loanId );
        if (!optionalLoan.isPresent()) {
            throw new LoanNotFoundException( "loan " + loanId + " not found" );
        }

        Loan loan=optionalLoan.get();
        if (loan.isExtended()) {
            throw new ImpossibleExtendLoanException( "this loan " + loanId + " has already been extended" );
        }


        Date date=makePeriodDate( 30, loan.getLoanEndDate() );
        loan.setLoanEndDate( date );
        loan.setExtended( true );

        loan=loanRepository.save( loan );
        return loanToLoanDto( loan );
    }

    @Override
    public LoanDto returnLoan(Long loanId) throws LoanNotFoundException {
        Optional<Loan> optionalLoan=loanRepository.findById( loanId );
        if (!optionalLoan.isPresent()) {
            throw new LoanNotFoundException( "loan " + loanId + " not found" );
        }
        Loan loan=optionalLoan.get();
        loan.setReturned( true );

        loan=loanRepository.save( loan );
        return loanToLoanDto( loan );
    }


    @Override
    public List<LoanDto> findDelay() {
        List<Loan> loans=loanRepository.searchDelay();
        List<LoanDto> loanDtos=new ArrayList<>();
        for (Loan loan : loans) {
            LoanDto loanDto=loanToLoanDto( loan );
            loanDtos.add( loanDto );
        }
        return loanDtos;
    }


    @Override
    public void deleteById(Long id)
            throws LoanNotFoundException, ImpossibleDeleteLoanException {
        Optional<Loan> optionalLoan=loanRepository.findById( id );
        if (!optionalLoan.isPresent()) {
            throw new LoanNotFoundException( "loan " + id + " not found" );
        } else if (!optionalLoan.get().isReturned()) {
            throw new ImpossibleDeleteLoanException( "copy of loan " + id + " have not been returned" );
        }
        loanRepository.deleteById( id );
        Loan loan=optionalLoan.get();
        loan.getCopy().setAvailable( true );
        copyRepository.save( loan.getCopy() );
    }


    private LoanDto loanToLoanDto(Loan loan) {
        LoanDto loanDto=new LoanDto();
        loanDto.setId( loan.getId() );
        loanDto.setLoanStartDate( loan.getLoanStartDate() );
        loanDto.setLoanEndDate( loan.getLoanEndDate() );
        loanDto.setExtended( loan.isExtended() );
        loanDto.setReturned( loan.isReturned() );

        User user=loan.getUser();
        UserDto userDto=new UserDto();
        userDto.setUserId( user.getId() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );
        userDto.setEmail( user.getEmail() );
        loanDto.setUserDto( userDto );

        loanDto.setCopyDto( makeCopyDto( loan.getCopy() ) );

        return loanDto;
    }


    private Loan loanDtoToLoan(LoanDto loanDto) {
        Loan loan=new Loan();
        loan.setId( loanDto.getId() );
        loan.setLoanStartDate( loanDto.getLoanStartDate() );
        loan.setLoanEndDate( loanDto.getLoanEndDate() );
        loan.setExtended( loanDto.isExtended() );
        loan.setReturned( loanDto.isReturned() );

        User user=new User();
        user.setId( loanDto.getUserDto().getUserId() );
        user.setFirstName( loanDto.getUserDto().getFirstName() );
        user.setLastName( loanDto.getUserDto().getLastName() );
        user.setEmail( loanDto.getUserDto().getEmail() );
        loan.setUser( user );

        loan.setCopy( makeCopy( loanDto.getCopyDto() ) );

        return loan;

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


    private CopyDto makeCopyDto(Copy copy) {
        CopyDto copyDto=new CopyDto();
        copyDto.setId( copy.getId() );
        copyDto.setAvailable( copy.isAvailable() );
        copyDto.setLibraryDto( makeLibraryDto( copy.getLibrary() ) );
        copyDto.setBookDto( makeBookDto( copy.getBook() ) );
        return copyDto;
    }

    private Copy makeCopy(CopyDto copyDto) {
        Copy copy=new Copy();
        copy.setId( copyDto.getId() );
        copy.setAvailable( copyDto.isAvailable() );
        copy.setLibrary( makeLibrary( copyDto.getLibraryDto() ) );
        copy.setBook( makeBook( copyDto.getBookDto() ) );
        return copy;
    }


    private BookDto makeBookDto(Book book) {

        BookDto bookDto=new BookDto();
        bookDto.setBookId( book.getBookId() );
        bookDto.setTitle( book.getTitle() );
        bookDto.setIsbn( book.getIsbn() );
        bookDto.setSummary( book.getSummary() );
        bookDto.setAuthorDto( makeAuthorDto( book.getAuthor() ) );
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


    private Date makePeriodDate(int numberOfDays, Date initialEndDate) {

        LocalDate localDate=LocalDate.now();

        if (initialEndDate != null) {
            localDate=initialEndDate.toInstant().atZone( ZoneId.systemDefault() ).toLocalDate();
        }

        localDate=localDate.plusDays( numberOfDays );
        Instant instant=localDate.atStartOfDay( ZoneId.systemDefault() ).toInstant();
        Date date=Date.from( instant );
        return date;
    }


}
