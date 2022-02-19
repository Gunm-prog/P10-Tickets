package com.emilie.Lib10.Models;

import com.emilie.Lib10.Models.Entities.Address;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AddressUnitTest {

    Address addressUnderTest = new Address();

    @Test
    void addressNumberUT() {
        addressUnderTest.setNumber( 445565 );
        assertThat( addressUnderTest.getNumber() ).isEqualTo( 445565 );
    }

    @Test
    void addressStreetUT() {
        addressUnderTest.setStreet("Adbygaïl street");
        assertThat( addressUnderTest.getStreet() ).isEqualTo("Adbygaïl street");
    }

    @Test
    void addressZipCodeUT() {
        addressUnderTest.setZipCode( "4567U");
        assertThat( addressUnderTest.getZipCode() ).isEqualTo(  "4567U") ;
    }

    @Test
    void addressCityUT() {
        addressUnderTest.setCity("big city");
        assertThat( addressUnderTest.getCity() ).isEqualTo( "big city");
    }
}
