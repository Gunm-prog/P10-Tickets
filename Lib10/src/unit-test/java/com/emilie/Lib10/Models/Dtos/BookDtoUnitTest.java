package com.emilie.Lib10.Models.Dtos;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

public class BookDtoUnitTest {

    BookDto bookDtoUnderTest = new BookDto();

    @Test
    void bookDtoIdUT() {
        bookDtoUnderTest.setBookId(999999999999999999L);
        assertThat( bookDtoUnderTest.getBookId() ).isInstanceOf( Long.class );
        assertThat( bookDtoUnderTest.getBookId() ).isEqualTo( 999999999999999999L );
    }

    @Test
    void bookDtoTitleUT() {
        bookDtoUnderTest.setTitle("title");
        assertThat( bookDtoUnderTest.getTitle() ).isInstanceOf( String.class );
        assertThat( bookDtoUnderTest.getTitle() ).isEqualTo( "title" );
    }

    @Test
    void bookDtoIsbnUT() {
        bookDtoUnderTest.setIsbn("86787YHBH");
        assertThat( bookDtoUnderTest.getIsbn() ).isInstanceOf( String.class );
        assertThat( bookDtoUnderTest.getIsbn() ).isEqualTo("86787YHBH");
    }

    @Test
    void bookDtoSummaryUT() {
        bookDtoUnderTest.setSummary("summary summary summary");
        assertThat( bookDtoUnderTest.getSummary() ).isInstanceOf( String.class );
        assertThat( bookDtoUnderTest.getSummary() ).isEqualTo("summary summary summary");
    }

    @Test
    void bookDtoAuthorUT() {
        bookDtoUnderTest.setAuthorDto( mock( AuthorDto.class ));
        assertThat( bookDtoUnderTest.getAuthorDto() ).isInstanceOf( AuthorDto.class );
    }

    @Test
    void bookDtoCopiesUT() {
        HashSet<CopyDto> copies = new HashSet<>();
        copies.add( mock(CopyDto.class ));
        copies.add( mock(CopyDto.class ));
        bookDtoUnderTest.setCopyDtos( copies );
        assertThat( bookDtoUnderTest.getCopyDtos() ).isInstanceOf( HashSet.class );
        assertThat( bookDtoUnderTest.getCopyDtos().size() ).isEqualTo( 2 );
    }

    @Test
    void bookDtoReservationsUT() {
        ArrayList<ReservationDto> reservationDtos = new ArrayList<>();
        reservationDtos.add( mock(ReservationDto.class ));
        reservationDtos.add( mock(ReservationDto.class ));
        bookDtoUnderTest.setReservations( reservationDtos );
        assertThat( bookDtoUnderTest.getReservations() ).isInstanceOf( ArrayList.class );
        assertThat( bookDtoUnderTest.getReservations().size() ).isEqualTo( 2 );
    }

    @Test
    void bookDtoAvailabilityUT() {
        bookDtoUnderTest.setAvailable( true );
        assertThat( bookDtoUnderTest.isAvailable() ).isInstanceOf( Boolean.class );
        assertThat( bookDtoUnderTest.isAvailable() ).isEqualTo( true );
        bookDtoUnderTest.setAvailable( false );
        assertThat( bookDtoUnderTest.isAvailable() ).isEqualTo( false );
    }

    @Test
    void bookDtoMaxReservationUT() {
        bookDtoUnderTest.setMaxReservation(4);
        assertThat( bookDtoUnderTest.getMaxReservation() ).isInstanceOf( Integer.class );
        assertThat( bookDtoUnderTest.getMaxReservation() ).isEqualTo( 4 );
    }
}
