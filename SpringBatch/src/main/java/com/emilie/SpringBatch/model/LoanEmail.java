package com.emilie.SpringBatch.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class LoanEmail {

    private User userDto;
    private Book bookDto;
    private LocalDate expectedBookReturn;
    private String message;
}
