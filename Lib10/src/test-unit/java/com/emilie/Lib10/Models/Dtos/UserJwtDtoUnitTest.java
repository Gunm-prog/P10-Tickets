package com.emilie.Lib10.Models.Dtos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserJwtDtoUnitTest {

    UserJwtDto userJwtDtoUnderTest = new UserJwtDto();

    @Test
    void userJwtDtoUsernameUT() {
        userJwtDtoUnderTest.setUsername("email@domain.net");
        assertThat( userJwtDtoUnderTest.getUsername() ).isInstanceOf( String.class );
        assertThat( userJwtDtoUnderTest.getUsername() ).isEqualTo( "email@domain.net" );
    }

    @Test
    void userJwtDtoAuthorities() {
        assertThat( userJwtDtoUnderTest.getAuthorities() ).isEqualTo( null );
    }

    @Test
    void userJwtDtoPasswordUT() {
        userJwtDtoUnderTest.setPassword("password");
        assertThat( userJwtDtoUnderTest.getPassword() ).isInstanceOf( String.class );
        assertThat( userJwtDtoUnderTest.getPassword() ).isEqualTo( "password" );
    }

    @Test
    void userJwtDtoAccountNonExpiredUT() {
        assertThat( userJwtDtoUnderTest.isAccountNonExpired() ).isInstanceOf( Boolean.class );

    }

    @Test
    void userJwtDtoAccountNonLockedUT() {
        assertThat( userJwtDtoUnderTest.isAccountNonLocked() ).isInstanceOf( Boolean.class );
    }

    @Test
    void userJwtDtoCredentialsNonExpiredUT() {
        assertThat( userJwtDtoUnderTest.isCredentialsNonExpired() ).isInstanceOf( Boolean.class );
    }

    @Test
    void userJwtDtoEnabledUT() {
        assertThat( userJwtDtoUnderTest.isEnabled() ).isInstanceOf( Boolean.class );
    }
}
