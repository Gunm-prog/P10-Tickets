package com.emilie.Lib10.Models;

import com.emilie.Lib10.Models.Entities.Author;
import com.emilie.Lib10.Models.Entities.Book;
import com.emilie.Lib10.Models.Entities.Copy;
import com.emilie.Lib10.Models.Entities.Reservation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class BookUnitTest {

    Book bookunderTest = new Book();

    @Test
    void bookIdUT(){
        bookunderTest.setBookId( 999999999999999999L );
        assertThat( bookunderTest.getBookId() ).isInstanceOf( Long.class);
        assertThat( bookunderTest.getBookId() ).isEqualTo( 999999999999999999L );
    }

    @Test
    public void bookTitleUT(){
        bookunderTest.setTitle( "book title" );
        assertThat( bookunderTest.getTitle() ).isEqualTo( "book title" );
    }

    @Test
    public void bookIsbnUT(){
        bookunderTest.setIsbn( "isbnBook" );
        assertThat( bookunderTest.getIsbn() ).isEqualTo( "isbnBook" );
    }

    @Test
    public void bookSummaryUT(){
        StringBuilder summary = new StringBuilder();
        for(int i=0; i<80; i++){ summary.append(" Summary_ "); }
        bookunderTest.setSummary(summary.toString());

        assertThat( bookunderTest.getSummary() ).isEqualTo(summary.toString());
        assertThat( bookunderTest.getSummary().length() ).isLessThanOrEqualTo( 800 );
    }

    @Test
    void bookAuthorUT(){
        bookunderTest.setAuthor( mock( Author.class ));
        assertThat( bookunderTest.getAuthor() ).isInstanceOf( Author.class );
    }

    @Test
    void bookCopiesUT(){
        Set<Copy> copies = new HashSet<>();
        for(int i=0; i<10; i++){
            copies.add( mock(Copy.class ) );
        }
        bookunderTest.setCopies( copies );

        assertThat( bookunderTest.getCopies() ).isInstanceOf( HashSet.class );
        assertThat( bookunderTest.getCopies().size() ).isEqualTo( 10 );
    }

    @Test
    void bookReservationsUT(){
        List<Reservation> reservations = new ArrayList<>();
        for(int i=0; i<10; i++){
            reservations.add( mock(Reservation.class ) );
        }
        bookunderTest.setReservationList( reservations );

        assertThat( bookunderTest.getReservationList() ).isInstanceOf( ArrayList.class );
        assertThat( bookunderTest.getReservationList().size() ).isEqualTo( 10 );
    }
}
