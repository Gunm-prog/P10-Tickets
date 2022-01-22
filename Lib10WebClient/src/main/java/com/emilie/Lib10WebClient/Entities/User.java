package com.emilie.Lib10WebClient.Entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class User {

    private Long userId;
    private String username;
    private String email;
    private String password;
    private String lastName;
    private String firstName;
    private Address addressDto;
    private Set<Loan> loanDtos;


}
