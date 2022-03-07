package com.emilie.SpringBatch.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class Reservation implements Serializable{

    private static final long serialVersionUID=1L;

    private Long id;
    private LocalDateTime reservationStartDate;
    private LocalDateTime reservationEndDate;
    private boolean isActive;
    private User userDto;
    private Book bookDto;
//    private CopyDto copyDto;


}
