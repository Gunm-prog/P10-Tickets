package com.emilie.Lib10.Models;

import com.emilie.Lib10.Models.Entities.Copy;
import com.emilie.Lib10.Models.Entities.Loan;
import com.emilie.Lib10.Models.Entities.User;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
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
        loanUnderTest.setLoanStartDate( new Date() );
        assertThat( loanUnderTest.getLoanStartDate() ).isEqualToIgnoringMillis( Calendar.getInstance().getTime() );
    }

    @Test
    void loanEndDateUT() {
        loanUnderTest.setLoanEndDate( new Date() );
        assertThat( loanUnderTest.getLoanEndDate() ).isEqualToIgnoringMillis( Calendar.getInstance().getTime() );
    }

    @Test
    void loanExtendUT() {
        loanUnderTest.setExtended( true );
        assertThat(loanUnderTest.isExtended() ).isInstanceOf( Boolean.class );
        loanUnderTest.setExtended( false );
        assertThat(loanUnderTest.isExtended() ).isInstanceOf( Boolean.class );
    }

    @Test
    void loanRetrunedUT() {
        loanUnderTest.setReturned( true );
        assertThat( loanUnderTest.isReturned() ).isInstanceOf( Boolean.class );
        loanUnderTest.setReturned( false );
        assertThat( loanUnderTest.isReturned() ).isInstanceOf( Boolean.class );
    }
}
