
package com.emilie.Lib7.Services.impl;


import com.emilie.Lib7.Exceptions.ImpossibleDeleteLibraryException;
import com.emilie.Lib7.Exceptions.LibraryAlreadyExistException;
import com.emilie.Lib7.Exceptions.LibraryNotFoundException;
import com.emilie.Lib7.Models.Dtos.*;
import com.emilie.Lib7.Models.Entities.*;
import com.emilie.Lib7.Repositories.BookRepository;
import com.emilie.Lib7.Repositories.CopyRepository;
import com.emilie.Lib7.Repositories.LibraryRepository;
import com.emilie.Lib7.Services.contract.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;


    @Autowired
    public LibraryServiceImpl(LibraryRepository libraryRepository,
                              BookRepository bookRepository,
                              CopyRepository copyRepository) {

        this.libraryRepository=libraryRepository;
        this.bookRepository=bookRepository;
        this.copyRepository=copyRepository;
    }


    @Override
    public List<LibraryDto> findAll() {
        List<Library> libraries=libraryRepository.findAll();
        List<LibraryDto> libraryDtos=new ArrayList<>();
        for (Library library : libraries) {
            LibraryDto libraryDto=libraryToLibraryDto( library );
            libraryDtos.add( libraryDto );
        }
        return libraryDtos;
    }


    @Override
    public LibraryDto findById(Long id) throws LibraryNotFoundException {
        Optional<Library> optionalLibrary=libraryRepository.findById( id );
        if (!optionalLibrary.isPresent()) {
            throw new LibraryNotFoundException( "Library not found" );
        }
        Library library=optionalLibrary.get();
        return libraryToLibraryDto( library );
    }

    @Override
    public LibraryDto save(LibraryDto libraryDto) throws LibraryAlreadyExistException {
        Optional<Library> optionalLibrary=libraryRepository.findByName( libraryDto.getName() );
        if (optionalLibrary.isPresent()) {
            throw new LibraryAlreadyExistException( "library already exists" );
        }
        Library library=libraryDtoToLibrary( libraryDto );
        library=libraryRepository.save( library );
        return libraryToLibraryDto( library );
    }

    @Override
    public LibraryDto update(LibraryDto libraryDto) throws LibraryNotFoundException {
        Optional<Library> optionalLibrary=libraryRepository.findById( libraryDto.getLibraryId() );
        if (!optionalLibrary.isPresent()) {
            throw new LibraryNotFoundException( "library not found" );
        }
        Library library=optionalLibrary.get();
        library.setName( libraryDto.getName() );
        library.setPhoneNumber( libraryDto.getPhoneNumber() );
        library=libraryRepository.save( library );
        return libraryToLibraryDto( library );
    }

    @Override
    public boolean deleteById(Long id) throws LibraryNotFoundException, ImpossibleDeleteLibraryException {
        Optional<Library> optionalLibrary=libraryRepository.findById( id );
        if (!optionalLibrary.isPresent()) {
            throw new LibraryNotFoundException( "library not found" );
        } else if (optionalLibrary.get().getCopies().size() > 0) {
            throw new ImpossibleDeleteLibraryException( "This library " + id + " have existing copies" );
        }
        try {
            libraryRepository.deleteById( id );
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    @Override
    public Set<CopyDto> findCopiesByLibraryId(Long id) {
        LibraryDto libraryDto=findById( id );
        return libraryDto.getCopyDtos();
    }


    private LibraryDto libraryToLibraryDto(Library library) {
        LibraryDto libraryDto=new LibraryDto();
        libraryDto.setLibraryId( library.getLibraryId() );
        libraryDto.setName( library.getName() );
        libraryDto.setPhoneNumber( library.getPhoneNumber() );

        Set<CopyDto> copyDtos=new HashSet<>();
        if (library.getCopies() != null) {
            for (Copy copy : library.getCopies()) {
                CopyDto copyDto=new CopyDto();
                copyDto.setId( copy.getId() );
                copyDto.setAvailable( copy.isAvailable() );

                BookDto bookDto=new BookDto();
                bookDto.setBookId( copy.getBook().getBookId() );
                bookDto.setTitle( copy.getBook().getTitle() );
                bookDto.setIsbn( copy.getBook().getIsbn() );
                bookDto.setSummary( copy.getBook().getSummary() );

                AuthorDto authorDto=new AuthorDto();
                authorDto.setAuthorId( copy.getBook().getAuthor().getAuthorId() );
                authorDto.setLastName( copy.getBook().getAuthor().getLastName() );
                authorDto.setFirstName( copy.getBook().getAuthor().getFirstName() );

                bookDto.setAuthorDto( authorDto );


                copyDto.setBookDto( bookDto );


                copyDtos.add( copyDto );
            }
            libraryDto.setCopyDtos( copyDtos );
        }

        libraryDto.setAddressDto( makeAddressDto( library.getAddress() ) );

        return libraryDto;

    }

    private Library libraryDtoToLibrary(LibraryDto libraryDto) {
        Library library=new Library();
        library.setLibraryId( libraryDto.getLibraryId() );
        library.setName( libraryDto.getName() );
        library.setPhoneNumber( libraryDto.getPhoneNumber() );


        Set<Copy> copies=new HashSet<>();
        if (libraryDto.getCopyDtos() != null) {
            for (CopyDto copyDto : libraryDto.getCopyDtos()) {
                Copy copy=new Copy();
                copy.setId( copyDto.getId() );
                copy.setAvailable( copyDto.isAvailable() );
                copies.add( copy );

                Book book=new Book();
                book.setBookId( copyDto.getBookDto().getBookId() );
                book.setTitle( copyDto.getBookDto().getTitle() );
                book.setIsbn( copyDto.getBookDto().getIsbn() );
                book.setSummary( copyDto.getBookDto().getSummary() );

                Author author=new Author();
                author.setAuthorId( copyDto.getBookDto().getAuthorDto().getAuthorId() );
                author.setLastName( copyDto.getBookDto().getAuthorDto().getLastName() );
                author.setFirstName( copyDto.getBookDto().getAuthorDto().getFirstName() );

                book.setAuthor( author );


                copy.setBook( book );


                copies.add( copy );

            }
            library.setCopies( copies );
        }

        library.setAddress( makeAddress( libraryDto.getAddressDto() ) );

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

}
