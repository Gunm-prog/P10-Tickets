package com.emilie.Lib10.Models.Entities;

import com.emilie.Lib10.Models.Entities.Book;
import com.emilie.Lib10.Models.Entities.Reservation;
import com.emilie.Lib10.Models.Entities.User;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ReservationUnitTest {
    
    Reservation reservationUnderTest = new Reservation();

    @Test
    void reservationIdUT() {
        reservationUnderTest.setId( 999999999999999999L );
        assertThat( reservationUnderTest.getId() ).isInstanceOf( Long.class );
        assertThat( reservationUnderTest.getId() ).isEqualTo( 999999999999999999L );
    }

    @Test
    void reservationBookUT() {
        reservationUnderTest.setBook( mock(Book.class) );
        assertThat( reservationUnderTest.getBook() ).isInstanceOf( Book.class );
    }

    @Test
    void reservationUserUT() {
        reservationUnderTest.setUser( mock( User.class ));
        assertThat( reservationUnderTest.getUser() ).isInstanceOf( User.class );
    }

    @Test
    void reservationStartDateUT() {
        LocalDateTime d = LocalDateTime.now();
        reservationUnderTest.setReservationStartDate( d );
        assertThat( reservationUnderTest.getReservationStartDate() ).isEqualTo( d );
    }

    @Test
    void reservationEndDateUT() {
        LocalDateTime d = LocalDateTime.now();
        reservationUnderTest.setReservationEndDate( d );
        assertThat( reservationUnderTest.getReservationEndDate() ).isEqualTo( d );
    }

    @Test
    void loanIsActiveUT() {
        reservationUnderTest.setActive( true );
        assertThat( reservationUnderTest.isActive() ).isInstanceOf( Boolean.class );
        assertThat( reservationUnderTest.isActive() ).isEqualTo( true );
        reservationUnderTest.setActive( false );
        assertThat( reservationUnderTest.isActive() ).isInstanceOf( Boolean.class );
        assertThat( reservationUnderTest.isActive() ).isEqualTo( false );
    }
}
