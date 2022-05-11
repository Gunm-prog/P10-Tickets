package com.emilie.Lib10.Models.Dtos;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

public class ReservationDtoUnitTest {

    ReservationDto reservationDtoUnderTest = new ReservationDto();

    @Test
    void reservationDtoIdUT() {
        reservationDtoUnderTest.setId(999999999999999999L);
        assertThat( reservationDtoUnderTest.getId() ).isInstanceOf( Long.class );
        assertThat( reservationDtoUnderTest.getId() ).isEqualTo( 999999999999999999L );
    }

    @Test
    void reservationDtoStartDateUT() {
        LocalDateTime d = LocalDateTime.now();
        reservationDtoUnderTest.setReservationStartDate( d );
        assertThat( reservationDtoUnderTest.getReservationStartDate() ).isInstanceOf( LocalDateTime.class );
        assertThat( reservationDtoUnderTest.getReservationStartDate() ).isEqualTo( d );
    }

    @Test
    void reservationDtoEndDateUT() {
        LocalDateTime d = LocalDateTime.now();
        reservationDtoUnderTest.setReservationEndDate( d );
        assertThat( reservationDtoUnderTest.getReservationEndDate() ).isInstanceOf( LocalDateTime.class );
        assertThat( reservationDtoUnderTest.getReservationEndDate() ).isEqualTo( d );
    }

    @Test
    void reservationDtoActiveUT() {
        reservationDtoUnderTest.setActive( true );
        assertThat( reservationDtoUnderTest.isActive() ).isInstanceOf( Boolean.class );
        assertThat( reservationDtoUnderTest.isActive() ).isEqualTo( true );
        reservationDtoUnderTest.setActive( false );
        assertThat( reservationDtoUnderTest.isActive() ).isEqualTo( false );
    }

    @Test
    void reservationUserUT() {
        reservationDtoUnderTest.setUserDto( mock(UserDto.class ));
        assertThat( reservationDtoUnderTest.getUserDto() ).isInstanceOf( UserDto.class );
    }

    @Test
    void reservationBookUT() {
        reservationDtoUnderTest.setBookDto( mock(BookDto.class ));
        assertThat( reservationDtoUnderTest.getBookDto() ).isInstanceOf( BookDto.class );
    }

    @Test
    void reservationMinExpectedReturnDateUt() {
        Date d = new Date();
        reservationDtoUnderTest.setMinExpectedReturnDate( d );
        assertThat( reservationDtoUnderTest.getMinExpectedReturnDate() ).isInstanceOf( Date.class );
        assertThat( reservationDtoUnderTest.getMinExpectedReturnDate() ).isEqualTo( d );
    }

    @Test
    void reservationNmbReservationUT() {
        reservationDtoUnderTest.setNmbReservation( 4 );
        assertThat( reservationDtoUnderTest.getNmbReservation() ).isInstanceOf( Integer.class );
        assertThat( reservationDtoUnderTest.getNmbReservation() ).isEqualTo( 4 );
    }

    @Test
    void reservationUserPositionUT() {
        reservationDtoUnderTest.setUserPosition(2);
        assertThat( reservationDtoUnderTest.getUserPosition() ).isInstanceOf( Integer.class );
        assertThat( reservationDtoUnderTest.getUserPosition() ).isEqualTo( 2 );
    }
}
