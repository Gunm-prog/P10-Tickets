package com.emilie.Lib10.Models.Dtos;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

public class AuthorDtoUnitTest {

    AuthorDto authorDtoUnderTest = new AuthorDto();

    @Test
    void authorDtoIdUT() {
        authorDtoUnderTest.setAuthorId(999999999999999999L);
        assertThat( authorDtoUnderTest.getAuthorId() ).isInstanceOf( Long.class );
        assertThat( authorDtoUnderTest.getAuthorId() ).isEqualTo( 999999999999999999L );
    }

    @Test
    void authorDtoFirstnameUT() {
        authorDtoUnderTest.setFirstName("firstname");
        assertThat( authorDtoUnderTest.getFirstName() ).isInstanceOf( String.class );
        assertThat( authorDtoUnderTest.getFirstName() ).isEqualTo( "firstname" );
    }

    @Test
    void authorDtoLastnameUT() {
        authorDtoUnderTest.setLastName("lastname");
        assertThat( authorDtoUnderTest.getLastName() ).isInstanceOf( String.class );
        assertThat( authorDtoUnderTest.getLastName() ).isEqualTo( "lastname" );
    }

    @Test
    void authorDtoBooksUT() {
        HashSet<BookDto> books = new HashSet<>();
        books.add( mock ( BookDto.class ));
        books.add( mock ( BookDto.class ));
        authorDtoUnderTest.setBookDtos( books );
        assertThat( authorDtoUnderTest.getBookDtos() ).isInstanceOf( HashSet.class );
        assertThat( authorDtoUnderTest.getBookDtos().size() ).isEqualTo( 2 );
    }
}
