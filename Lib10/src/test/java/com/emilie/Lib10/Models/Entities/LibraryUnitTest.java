package com.emilie.Lib10.Models.Entities;

import com.emilie.Lib10.Models.Entities.Address;
import com.emilie.Lib10.Models.Entities.Copy;
import com.emilie.Lib10.Models.Entities.Library;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class LibraryUnitTest {

    Library libraryUnderTest  = new Library() ;

    @Test
    void libraryIdUT() {
        libraryUnderTest.setLibraryId( 999999999999999999L );
        assertThat( libraryUnderTest.getLibraryId() ).isInstanceOf( Long.class );
        assertThat( libraryUnderTest.getLibraryId() ).isEqualTo( 999999999999999999L );
    }

    @Test
    public void libraryNameUT(){
        libraryUnderTest.setName("lib name");
        assertThat(libraryUnderTest.getName()).isEqualTo(("lib name"));
    }

    @Test
    public void libraryPhoneNumberUT(){
        libraryUnderTest.setPhoneNumber("0312345678");
        assertThat( libraryUnderTest.getPhoneNumber() ).isEqualTo("0312345678");
        assertThat( libraryUnderTest.getPhoneNumber().length() ).isLessThanOrEqualTo( 20 );
    }

    @Test
    public void libraryAdressUT(){
        libraryUnderTest.setAddress( mock(Address.class) );
        assertThat(libraryUnderTest.getAddress()).isInstanceOf(Address.class);
    }

    @Test
    public void libraryCopiesUT(){
        Set<Copy> copies = new HashSet<>();
        for(int i=0; i<20; i++){
            copies.add( mock( Copy.class ) );
        }
        libraryUnderTest.setCopies( copies );
        assertThat( libraryUnderTest.getCopies() ).isInstanceOf( HashSet.class );
        assertThat( libraryUnderTest.getCopies().size() ).isEqualTo( 20 );
    }
}
