package com.emilie.Lib10.Models.Dtos;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class UserDtoUnitTest {

    UserDto userDtoUnderTest = new UserDto();

    @Test
    void userDtoIdUT() {
        userDtoUnderTest.setUserId( 999999999999999999L );
        assertThat( userDtoUnderTest.getUserId() ).isInstanceOf( Long.class );
        assertThat( userDtoUnderTest.getUserId() ).isEqualTo( 999999999999999999L );
    }

    @Test
    void userDtoFirstnameUT() {
        userDtoUnderTest.setFirstName("firstname user ");
        assertThat( userDtoUnderTest.getFirstName() ).isEqualTo( "firstname user ");
    }

    @Test
    void userDtoLastnameUT() {
        userDtoUnderTest.setLastName("LastName user ");
        assertThat( userDtoUnderTest.getLastName() ).isEqualTo( "LastName user ");
    }

    @Test
    void userDtoEmailUT() {
        userDtoUnderTest.setEmail("email@mial.net");
        assertThat( userDtoUnderTest.getEmail() ).isEqualTo("email@mial.net");
    }

    @Test
    void userDtoPasswordUT() {
        userDtoUnderTest.setPassword("password");
        assertThat( userDtoUnderTest.getPassword() ).isEqualTo("password");
    }

    @Test
    void userDtoActiveUT() {
        userDtoUnderTest.setActive( false );
        assertThat( userDtoUnderTest.isActive() ).isInstanceOf( Boolean.class );
        assertThat( userDtoUnderTest.isActive() ).isEqualTo( false );
        userDtoUnderTest.setActive( true );
        assertThat( userDtoUnderTest.isActive() ).isEqualTo( true );
    }

    @Test
    void userDtoRolesUT() {
        userDtoUnderTest.setRoles("CUSTOMER");
        assertThat( userDtoUnderTest.getRoles() ).isInstanceOf( String.class );
        assertThat( userDtoUnderTest.getRoles() ).isEqualTo("CUSTOMER");
    }

    @Test
    void userDtoLoansUT() {
        HashSet<LoanDto> loans = new HashSet<>();
        loans.add( mock(LoanDto.class ));
        loans.add( mock(LoanDto.class ));
        userDtoUnderTest.setLoanDtos( loans );
        assertThat( userDtoUnderTest.getLoanDtos() ).isInstanceOf( HashSet.class );
        assertThat( userDtoUnderTest.getLoanDtos().size() ).isEqualTo( 2 );
    }

    @Test
    void userDtoCopiesUT() {
        userDtoUnderTest.setCopyDto( mock(CopyDto.class ));
        assertThat( userDtoUnderTest.getCopyDto() ).isInstanceOf( CopyDto.class );
    }

    @Test
    void userDtoAddressUT() {
        userDtoUnderTest.setAddressDto( mock(AddressDto.class ));
        assertThat( userDtoUnderTest.getAddressDto() ).isInstanceOf( AddressDto.class );
    }

    @Test
    void userDtoReservationsUT() {
        ArrayList<ReservationDto> reservationDtos = new ArrayList<>();
        reservationDtos.add( mock(ReservationDto.class ));
        reservationDtos.add( mock(ReservationDto.class ));
        userDtoUnderTest.setReservationDtos( reservationDtos );
        assertThat( userDtoUnderTest.getReservationDtos() ).isInstanceOf( ArrayList.class );
        assertThat( userDtoUnderTest.getReservationDtos().size() ).isEqualTo( 2 );
    }
}
