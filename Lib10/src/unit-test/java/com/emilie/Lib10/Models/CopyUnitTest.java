package com.emilie.Lib10.Models;

import com.emilie.Lib10.Models.Entities.Book;
import com.emilie.Lib10.Models.Entities.Copy;
import com.emilie.Lib10.Models.Entities.Library;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CopyUnitTest {

    Copy copyUnderTest = new Copy();

    @Test
    void copyIdUT(){
        copyUnderTest.setId( 999999999999999999L );
        assertThat( copyUnderTest.getId() ).isInstanceOf( Long.class);
        assertThat( copyUnderTest.getId() ).isEqualTo( 999999999999999999L );
    }

    @Test
    void copyAvailabilityUT() {
        copyUnderTest.setAvailable( false );
        assertThat( copyUnderTest.isAvailable() ).isEqualTo( false );
        copyUnderTest.setAvailable( true );
        assertThat( copyUnderTest.isAvailable() ).isEqualTo( true );
    }

    @Test
    void copyBookUT() {
        copyUnderTest.setBook( mock(Book.class ) );
        assertThat( copyUnderTest.getBook() ).isInstanceOf( Book.class );
    }

    @Test
    void copyLibraryUT() {
        copyUnderTest.setLibrary( mock( Library.class ));
        assertThat( copyUnderTest.getLibrary() ).isInstanceOf( Library.class );
    }

}
