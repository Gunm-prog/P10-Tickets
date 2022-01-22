package com.emilie.Lib10WebClient.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Copy {

    private Long id;
    private boolean available;
    private Book bookDto;
    private Library libraryDto;

}
