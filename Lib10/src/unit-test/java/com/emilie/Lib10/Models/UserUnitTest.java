package com.emilie.Lib10.Models;

import com.emilie.Lib10.Models.Entities.Address;
import com.emilie.Lib10.Models.Entities.Loan;
import com.emilie.Lib10.Models.Entities.Reservation;
import com.emilie.Lib10.Models.Entities.User;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class UserUnitTest {

    User userUnderTest = new User();

    @Test
    void userIdUT() {
        userUnderTest.setId( 999999999999999999L );
        assertThat( userUnderTest.getId() ).isInstanceOf( Long.class );
        assertThat( userUnderTest.getId() ).isEqualTo( 999999999999999999L );
    }

    @Test
    void userPasswordUT() {
        userUnderTest.setPassword("password");
        assertThat( userUnderTest.getPassword() ).isEqualTo( "password");
    }

    @Test
    void userIsActiveUT() {
        userUnderTest.setActive( false );
        assertThat( userUnderTest.isActive() ).isEqualTo( false );
        userUnderTest.setActive( true );
        assertThat( userUnderTest.isActive() ).isEqualTo( true );
    }

    @Test
    void userRolesUT() {
        userUnderTest.setRoles("CUSTOMER");
        assertThat( userUnderTest.getRoles() ).isEqualTo( "CUSTOMER");
        userUnderTest.setRoles("EMPLOYEE");
        assertThat( userUnderTest.getRoles() ).isEqualTo( "EMPLOYEE");
        userUnderTest.setRoles("ADMIN");
        assertThat( userUnderTest.getRoles() ).isEqualTo( "ADMIN");

        userUnderTest.setRoles("ADMIN,EMPLOYEE,CUSTOMER");
        assertThat( userUnderTest.getRolesList().size() ).isEqualTo( 3 );
        assertThat( userUnderTest.getRolesList().get(0) ).isEqualTo( "ADMIN" );
        assertThat( userUnderTest.getRolesList().get(1) ).isEqualTo( "EMPLOYEE" );
        assertThat( userUnderTest.getRolesList().get(2) ).isEqualTo( "CUSTOMER" );
    }

    @Test
    void userFirstnameUT() {
        userUnderTest.setFirstName("firstname user ");
        assertThat( userUnderTest.getFirstName() ).isEqualTo( "firstname user ");
    }

    @Test
    void userLastnameUT() {
        userUnderTest.setLastName("LastName user ");
        assertThat( userUnderTest.getLastName() ).isEqualTo( "LastName user ");
    }

    @Test
    void userEmailUT() {
        userUnderTest.setEmail("email@mial.net");
        assertThat( userUnderTest.getEmail() ).isEqualTo("email@mial.net");
    }

    @Test
    void userUsernameUT() {
        userUnderTest.setEmail("email@mial.net");
        assertThat( userUnderTest.getUsername() ).isEqualTo( "email@mial.net");
    }

    @Test
    void userLoansUT() {
        Set<Loan> loans = new HashSet<>();
        for(int i=0; i<10; i++){
            loans.add( mock(Loan.class) );
        }
        userUnderTest.setLoans( loans );
        assertThat( userUnderTest.getLoans() ).isInstanceOf( HashSet.class );
        assertThat( userUnderTest.getLoans().size() ).isEqualTo( 10 );
    }

    @Test
    void usserReservationsUT() {
        Set<Reservation> reservations = new HashSet<>();
        for(int i=0; i<10; i++){
            reservations.add( mock(Reservation.class) );
        }
        userUnderTest.setReservations(   reservations );
        assertThat( userUnderTest.getReservations() ).isInstanceOf( HashSet.class );
        assertThat( userUnderTest.getReservations().size() ).isEqualTo( 10 );
    }

    @Test
    void userAddressUT() {
        userUnderTest.setAddress( mock( Address.class ));
        assertThat( userUnderTest.getAddress() ).isInstanceOf( Address.class );
    }
}
