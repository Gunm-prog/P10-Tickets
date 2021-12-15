package com.emilie.library7WebClient.Entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class Library {

    private Long libraryId;
    private String name;
    private String phoneNumber;
    private List<Copy> copies;
    private Address addressDto;
    private int nbCopies=0;
    private int nbAvailableCopies=0;

}
