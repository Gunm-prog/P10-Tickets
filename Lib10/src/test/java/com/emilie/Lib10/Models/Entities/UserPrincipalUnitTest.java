package com.emilie.Lib10.Models.Entities;

import com.emilie.Lib10.Models.Entities.User;
import com.emilie.Lib10.Models.Entities.UserPrincipal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserPrincipalUnitTest {

    static UserPrincipal userPrincipalUnderTest;

    @BeforeAll
    static void beforeAll() {
        User u = mock(User.class);
        when(u.getId()).thenReturn( 999999999999999999L );
        when(u.getLastName()).thenReturn( "lastname" );
        when(u.getFirstName()).thenReturn( "firstname" );

        ArrayList<String> rList = new ArrayList<>();
        rList.add("ADMIN");
        rList.add("EMPLOYEE");
        rList.add("CUSTOMER");
        when(u.getRolesList()).thenReturn( rList );

        when(u.getRoles()).thenReturn("ADMIN,EMPLOYEE,CUSTOMER");
        when(u.getPassword()).thenReturn("password");
        when(u.getUsername()).thenReturn("mail@mail.net");
        when(u.isActive()).thenReturn( true );

        userPrincipalUnderTest = new UserPrincipal( u );
    }

    @Test
    void userPrincipalIdUT() {
        assertThat( userPrincipalUnderTest.getUserId() ).isInstanceOf( Long.class );
        assertThat( userPrincipalUnderTest.getUserId() ).isEqualTo( 999999999999999999L );
    }

    @Test
    void userPrincipalLastnameUT() {
        assertThat( userPrincipalUnderTest.getLastname() ).isInstanceOf( String.class);
        assertThat( userPrincipalUnderTest.getLastname() ).isEqualTo( "lastname" );
    }

    @Test
    void userPrincipalFirstnameUT() {
        assertThat( userPrincipalUnderTest.getFirstname() ).isInstanceOf( String.class );
        assertThat( userPrincipalUnderTest.getFirstname() ).isEqualTo( "firstname" );
    }

    @Test
    void userPrincipalAuthoritiesUT() {
        assertThat( userPrincipalUnderTest.getAuthorities() ).isInstanceOf(Collection.class );
        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
            GrantedAuthority ga1=new SimpleGrantedAuthority( "ROLE_ADMIN" );
            grantedAuthorities.add( ga1 );
            GrantedAuthority ga2=new SimpleGrantedAuthority( "ROLE_EMPLOYEE" );
            grantedAuthorities.add( ga2 );
            GrantedAuthority ga3=new SimpleGrantedAuthority( "ROLE_CUSTOMER" );
            grantedAuthorities.add( ga3 );

        assertThat( userPrincipalUnderTest.getAuthorities().containsAll(grantedAuthorities));
    }

    @Test
    void userPrincipalRoleUT() {
        assertThat( userPrincipalUnderTest.getRole() ).isInstanceOf( String.class);
        assertThat( userPrincipalUnderTest.getRole()).isEqualTo("ADMIN,EMPLOYEE,CUSTOMER");
    }

    @Test
    void userPrincipalPasswordUT() {
        assertThat( userPrincipalUnderTest.getPassword() ).isInstanceOf( String.class );
    }

    @Test
    void userPrincipalUsernameUT() {
        assertThat( userPrincipalUnderTest.getUsername() ).isInstanceOf( String.class);
    }

    @Test
    void userPrincipalAccountNonExpiredUT() {
        assertThat( userPrincipalUnderTest.isAccountNonExpired() ).isInstanceOf(Boolean.class );
    }

    @Test
    void userPrincipalAccountNonLockedUT() {
        assertThat( userPrincipalUnderTest.isAccountNonLocked() ).isInstanceOf( Boolean.class );
    }

    @Test
    void userPrincipalCredentialsNonExpiredUT() {
        assertThat( userPrincipalUnderTest.isCredentialsNonExpired() ).isInstanceOf( Boolean.class );
    }

    @Test
    void userPrincipalIsEnabledUT() {
        assertThat( userPrincipalUnderTest.isEnabled() ).isInstanceOf( Boolean.class );
    }
}
