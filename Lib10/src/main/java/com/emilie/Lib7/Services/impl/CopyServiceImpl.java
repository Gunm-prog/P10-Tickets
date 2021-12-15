package com.emilie.Lib7.Services.impl;

import com.emilie.Lib7.Exceptions.*;
import com.emilie.Lib7.Models.Dtos.*;
import com.emilie.Lib7.Models.Entities.*;
import com.emilie.Lib7.Repositories.BookRepository;
import com.emilie.Lib7.Repositories.CopyRepository;
import com.emilie.Lib7.Repositories.LibraryRepository;
import com.emilie.Lib7.Services.contract.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CopyServiceImpl implements CopyService {

    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;


    @Autowired
    public CopyServiceImpl(CopyRepository copyRepository,
                           BookRepository bookRepository,
                           LibraryRepository libraryRepository) {

        this.copyRepository=copyRepository;
        this.bookRepository=bookRepository;
        this.libraryRepository=libraryRepository;
    }

    @Override
    public List<CopyDto> findAll() {
        return null;
    }

    @Override
    public List<CopyDto> searchCopies(Long libraryId, String title, String isbn, String firstName, String lastName) {

        List<Copy> copies;
        if (libraryId != null) {
            copies=copyRepository.searchCopiesByLibrary( libraryId, firstName, lastName, title, isbn );
        } else {
            copies=copyRepository.searchCopies( firstName, lastName, title, isbn );
        }
        List<CopyDto> copyDtos=new ArrayList<>();

        if (!copies.isEmpty()) {
            for (Copy copy : copies) {
                CopyDto copyDto1=copyToCopyDto( copy );
                copyDtos.add( copyDto1 );
            }
        }
        return copyDtos;
    }


    @Override
    public CopyDto findById(Long id) throws CopyNotFoundException {
        Optional<Copy> optionalCopy=copyRepository.findById( id );
        if (optionalCopy.isEmpty()) {
            throw new CopyNotFoundException( "copy " + id + " not found" );
        }
        Copy copy=optionalCopy.get();
        return copyToCopyDto( copy );

    }


    @Override
    public CopyDto save(CopyDto copyDto) throws BookNotFoundException, LibraryNotFoundException {
        Optional<Book> optionalBook=bookRepository.findById( copyDto.getBookDto().getBookId() );
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException( "book " + copyDto.getBookDto().getBookId() + " not found" );
        }
        Optional<Library> optionalLibrary=libraryRepository.findById( copyDto.getLibraryDto().getLibraryId() );
        if (!optionalLibrary.isPresent()) {
            throw new LibraryNotFoundException( "library " + copyDto.getLibraryDto().getLibraryId() + " not found" );
        }
        BookDto bookDto=makeBookDto( optionalBook.get() );
        copyDto.setBookDto( bookDto );

        LibraryDto libDto=makeLibraryDto( optionalLibrary.get() );
        copyDto.setLibraryDto( libDto );

        Copy copy=copyDtoToCopy( copyDto );
        copy.setAvailable( true );
        copy=copyRepository.save( copy );
        return copyToCopyDto( copy );
    }


    @Override
    public CopyDto update(CopyDto copyDto)
            throws CopyNotFoundException, BookNotFoundException, LibraryNotFoundException {
        Optional<Copy> optionalCopy=copyRepository.findById( copyDto.getId() );
        if (!optionalCopy.isPresent()) {
            throw new CopyNotFoundException( "copy " + copyDto.getId() + " not found" );
        }
        Optional<Book> optionalBook=bookRepository.findById( copyDto.getId() );
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException( "book " + copyDto.getId() + " not found" );
        }
        Optional<Library> optionalLibrary=libraryRepository.findById( copyDto.getLibraryDto().getLibraryId() );
        if (!optionalLibrary.isPresent()) {
            throw new LibraryNotFoundException( "library " + copyDto.getLibraryDto().getLibraryId() + " not found" );
        }

        Copy copy=optionalCopy.get();
        copy.setAvailable( copyDto.isAvailable() );

        copy.setBook( extractBookFromCopyDto( copyDto ) );
        copy.setLibrary( optionalLibrary.get() );

        copy=copyRepository.save( copy );

        return copyToCopyDto( copy );
    }


    @Override
    public void deleteById(Long id)
            throws CopyNotFoundException, ImpossibleExtendLoanException {
        Optional<Copy> optionalCopy=copyRepository.findById( id );
        if (!optionalCopy.isPresent()) {
            throw new CopyNotFoundException( "Copy " + id + " not found" );
        } else if (!optionalCopy.get().isAvailable()) {
            throw new ImpossibleDeleteCopyException( "This copy " + id + " have existing loan" );
        }
        copyRepository.deleteById( id );

    }


    private CopyDto copyToCopyDto(Copy copy) {
        CopyDto copyDto=new CopyDto();
        copyDto.setId( copy.getId() );
        copyDto.setAvailable( copy.isAvailable() );

        copyDto.setBookDto( makeBookDto( copy.getBook() ) );

        copyDto.setLibraryDto( makeLibraryDto( copy.getLibrary() ) );

        return copyDto;
    }

    private Copy copyDtoToCopy(CopyDto copyDto) {
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


    private Book extractBookFromCopyDto(CopyDto copyDto) {
        Book book=new Book();
        book.setBookId( copyDto.getBookDto().getBookId() );
        book.setTitle( copyDto.getBookDto().getTitle() );
        book.setIsbn( copyDto.getBookDto().getIsbn() );
        book.setSummary( copyDto.getBookDto().getSummary() );

        return book;
    }

}