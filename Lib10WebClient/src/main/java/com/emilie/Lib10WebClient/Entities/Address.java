package com.emilie.Lib10WebClient.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address {

    private int number;
    private String street;
    private String zipCode;
    private String city;


}
