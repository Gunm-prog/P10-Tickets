package com.emilie.Lib10.Models.Dtos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AddressDtoUnitTest {

    AddressDto addressDtoUnderTest = new AddressDto();

    @Test
    void addressDtoNumberUT() {
        addressDtoUnderTest.setNumber(45);
        assertThat( addressDtoUnderTest.getNumber() ).isInstanceOf( Integer.class );
        assertThat( addressDtoUnderTest.getNumber() ).isEqualTo( 45 );
    }

    @Test
    void addressDtoStreetUT() {
        addressDtoUnderTest.setStreet("streetname");
        assertThat( addressDtoUnderTest.getStreet() ).isInstanceOf( String.class );
        assertThat( addressDtoUnderTest.getStreet() ).isEqualTo( "streetname" );
    }

    @Test
    void addressDtoZipCodeUT() {
        addressDtoUnderTest.setZipCode("34YYR5");
        assertThat( addressDtoUnderTest.getZipCode() ).isInstanceOf( String.class );
        assertThat( addressDtoUnderTest.getZipCode() ).isEqualTo( "34YYR5");
    }

    @Test
    void addressDtoCityUT() {
        addressDtoUnderTest.setCity("cityname");
        assertThat( addressDtoUnderTest.getCity() ).isInstanceOf( String.class );
        assertThat( addressDtoUnderTest.getCity() ).isEqualTo( "cityname" );
    }
}
