package com.emilie.library7WebClient.Entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserAccountLogin {


    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;


}