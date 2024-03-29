package com.emilie.Lib10.Models.Entities;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class LoanUnitTest {

    Loan loanUnderTest = new Loan();

    @Test
    void loanIdUT() {
        loanUnderTest.setId( 999999999999999999L );
        assertThat( loanUnderTest.getId() ).isInstanceOf( Long.class );
        assertThat( loanUnderTest.getId() ).isEqualTo( 999999999999999999L );
    }

    @Test
    void loanCopyUT() {
        loanUnderTest.setCopy( mock(Copy.class) );
        assertThat( loanUnderTest.getCopy() ).isInstanceOf( Copy.class);
    }

    @Test
    void loanUserUT() {
        loanUnderTest.setUser( mock(User.class));
        assertThat( loanUnderTest.getUser() ).isInstanceOf( User.class);
    }

    @Test
    void loanStartDateUT() {
        Date d = new Date();
        loanUnderTest.setLoanStartDate( d );
        assertThat( loanUnderTest.getLoanStartDate() ).isEqualTo( d );
    }

    @Test
    void loanEndDateUT() {
        Date d = new Date();
        loanUnderTest.setLoanEndDate( d );
        assertThat( loanUnderTest.getLoanEndDate() ).isEqualTo( d );
    }

    @Test
    void loanExtendUT() {
        loanUnderTest.setExtended( true );
        assertThat(loanUnderTest.isExtended() ).isInstanceOf( Boolean.class );
        loanUnderTest.setExtended( false );
        assertThat(loanUnderTest.isExtended() ).isInstanceOf( Boolean.class );
    }

    @Test
    void loanReturnedUT() {
        loanUnderTest.setReturned( true );
        assertThat( loanUnderTest.isReturned() ).isInstanceOf( Boolean.class );
        loanUnderTest.setReturned( false );
        assertThat( loanUnderTest.isReturned() ).isInstanceOf( Boolean.class );
    }
}
