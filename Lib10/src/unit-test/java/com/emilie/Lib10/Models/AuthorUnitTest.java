package com.emilie.Lib10.Models;

import com.emilie.Lib10.Models.Entities.Author;
import com.emilie.Lib10.Models.Entities.Book;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class AuthorUnitTest {

    Author authorUnderTest = new Author();

    @Test
    void authorIdUT() {
        authorUnderTest.setAuthorId( 999999999999999999L );
        assertThat( authorUnderTest.getAuthorId() ).isInstanceOf( Long.class );
        assertThat( authorUnderTest.getAuthorId() ).isEqualTo( 999999999999999999L );
    }

    @Test
    public void authorFirstNameUT(){
        authorUnderTest.setFirstName("author firstname");
        assertThat(authorUnderTest.getFirstName()).isEqualTo("author firstname");
    }

    @Test
    void authorLastNameUT() {
        authorUnderTest.setLastName("author lastname");
        assertThat(authorUnderTest.getLastName()).isEqualTo("author lastname");
    }

    @Test
    void authorBooksUT() {
        Set<Book> books = new HashSet<>();
        for(int i=0; i<20; i++){
            books.add( mock(Book.class) );
        }
        authorUnderTest.setBooks( books );
        assertThat( authorUnderTest.getBooks() ).isInstanceOf( HashSet.class );
        assertThat( authorUnderTest.getBooks().size() ).isEqualTo( 20 );
    }
}
