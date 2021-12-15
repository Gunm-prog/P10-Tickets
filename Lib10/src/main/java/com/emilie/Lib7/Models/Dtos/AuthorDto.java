package com.emilie.Lib7.Models.Dtos;


import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class AuthorDto implements Serializable {

    public static final long serialVersionUID=1L;

    private Long authorId;
    private String firstName;
    private String lastName;
    private Set<BookDto> bookDtos;


}