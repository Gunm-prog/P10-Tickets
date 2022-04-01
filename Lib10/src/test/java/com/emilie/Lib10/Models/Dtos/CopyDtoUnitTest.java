package com.emilie.Lib10.Models.Dtos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

public class CopyDtoUnitTest {

    CopyDto copyDtoUnderTest = new CopyDto();

    @Test
    void copyDtoIdUT() {
        copyDtoUnderTest.setId(999999999999999999L);
        assertThat( copyDtoUnderTest.getId() ).isInstanceOf( Long.class );
        assertThat( copyDtoUnderTest.getId() ).isEqualTo( 999999999999999999L );
    }

    @Test
    void copyDtoAvailabilityUT() {
        copyDtoUnderTest.setAvailable( true );
        assertThat( copyDtoUnderTest.isAvailable() ).isInstanceOf( Boolean.class );
        assertThat( copyDtoUnderTest.isAvailable() ).isEqualTo( true );
        copyDtoUnderTest.setAvailable( false );
        assertThat( copyDtoUnderTest.isAvailable() ).isEqualTo( false );
    }

    @Test
    void copyDtoBookUT() {
        copyDtoUnderTest.setBookDto( mock( BookDto.class ));
        assertThat( copyDtoUnderTest.getBookDto() ).isInstanceOf( BookDto.class );
    }

    @Test
    void copyDtoLibraryUT() {
        copyDtoUnderTest.setLibraryDto( mock(LibraryDto.class ));
        assertThat( copyDtoUnderTest.getLibraryDto() ).isInstanceOf( LibraryDto.class );
    }
}
