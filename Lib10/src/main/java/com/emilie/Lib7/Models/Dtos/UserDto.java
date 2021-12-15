package com.emilie.Lib7.Models.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDto implements Serializable {

    private static final long serialVersionUID=1L;

    private Long userId;
    private String lastName;
    private String firstName;
    private String password;
    private String email;
    private boolean active;
    private String roles;
    private Set<LoanDto> loanDtos;
    private CopyDto CopyDto;
    private AddressDto addressDto=null;

}

