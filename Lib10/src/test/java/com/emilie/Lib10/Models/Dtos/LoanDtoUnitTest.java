package com.emilie.Lib10.Models.Dtos;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

public class LoanDtoUnitTest {

    LoanDto loanDtoUnderTest = new LoanDto();

    @Test
    void loanDtoIdUT() {
        loanDtoUnderTest.setId(999999999999999999L);
        assertThat( loanDtoUnderTest.getId() ).isInstanceOf( Long.class );
        assertThat( loanDtoUnderTest.getId() ).isEqualTo( 999999999999999999L );
    }

    @Test
    void loanStartDateUT() {
        Date d = new Date();
        loanDtoUnderTest.setLoanStartDate( d );
        assertThat( loanDtoUnderTest.getLoanStartDate() ).isInstanceOf( Date.class );
        assertThat( loanDtoUnderTest.getLoanStartDate() ).isEqualTo( d );
    }

    @Test
    void loanEndDateUT() {
        Date d = new Date();
        loanDtoUnderTest.setLoanEndDate( d );
        assertThat( loanDtoUnderTest.getLoanEndDate() ).isInstanceOf( Date.class );
        assertThat( loanDtoUnderTest.getLoanEndDate() ).isEqualTo( d );
    }

    @Test
    void loanDtoExtendUT() {
        loanDtoUnderTest.setExtended( true );
        assertThat( loanDtoUnderTest.isExtended() ).isInstanceOf( Boolean.class );
        assertThat( loanDtoUnderTest.isExtended() ).isEqualTo( true );
        loanDtoUnderTest.setExtended( false );
        assertThat( loanDtoUnderTest.isExtended() ).isEqualTo( false );
    }

    @Test
    void loanDtoReturnUT() {
        loanDtoUnderTest.setReturned( true );
        assertThat( loanDtoUnderTest.isReturned() ).isInstanceOf( Boolean.class );
        assertThat( loanDtoUnderTest.isReturned() ).isEqualTo( true );
        loanDtoUnderTest.setReturned( false );
        assertThat( loanDtoUnderTest.isReturned() ).isEqualTo( false );
    }

    @Test
    void loanDtoUserUT() {
        loanDtoUnderTest.setUserDto( mock(UserDto.class ));
        assertThat( loanDtoUnderTest.getUserDto() ).isInstanceOf(UserDto.class );
    }

    @Test
    void loanDtoCopyUT() {
        loanDtoUnderTest.setCopyDto( mock(CopyDto.class ));
        assertThat( loanDtoUnderTest.getCopyDto() ).isInstanceOf(CopyDto.class );
    }
}
