package com.emilie.Lib10.Models.Dtos;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

public class LibraryDtoUnitTest {

    LibraryDto libraryDtoUnderTest = new LibraryDto();

    @Test
    void libraryDtoIdUT() {
        libraryDtoUnderTest.setLibraryId(999999999999999999L);
        assertThat( libraryDtoUnderTest.getLibraryId() ).isInstanceOf( Long.class );
        assertThat( libraryDtoUnderTest.getLibraryId() ).isEqualTo( 999999999999999999L );
    }

    @Test
    void libraryDtoNameUT() {
        libraryDtoUnderTest.setName("libraryName");
        assertThat( libraryDtoUnderTest.getName() ).isInstanceOf( String.class );
        assertThat( libraryDtoUnderTest.getName() ).isEqualTo("libraryName");
    }

    @Test
    void libraryDtoPhoneNumberUT() {
        libraryDtoUnderTest.setPhoneNumber( "0312345678");
        assertThat( libraryDtoUnderTest.getPhoneNumber() ).isInstanceOf( String.class );
        assertThat( libraryDtoUnderTest.getPhoneNumber() ).isEqualTo("0312345678");
    }

    @Test
    void libraryDtoAddressUT() {
        libraryDtoUnderTest.setAddressDto( mock( AddressDto.class ));
        assertThat( libraryDtoUnderTest.getAddressDto() ).isInstanceOf( AddressDto.class );
    }

    @Test
    void libraryDtoCopiesUT() {
        HashSet<CopyDto> copies = new HashSet<>();
        copies.add( mock(CopyDto.class ));
        copies.add( mock(CopyDto.class ));
        libraryDtoUnderTest.setCopyDtos( copies );
        assertThat( libraryDtoUnderTest.getCopyDtos() ).isInstanceOf( HashSet.class);
        assertThat( libraryDtoUnderTest.getCopyDtos().size() ).isEqualTo( 2 );
    }
}
