package com.emilie.SpringBatch.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID=1L;


    private String lastName;
    private String firstName;
    private String email;

}