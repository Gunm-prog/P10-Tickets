package com.emilie.Lib7.Models.Dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressDto implements Serializable {

    public static final long serialVersionUID=1L;


    private int number;
    private String street;
    private String zipCode;
    private String city;

}
