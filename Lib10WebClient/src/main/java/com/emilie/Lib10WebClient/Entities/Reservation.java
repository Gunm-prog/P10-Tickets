package com.emilie.Lib10WebClient.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@NoArgsConstructor
public class Reservation implements Serializable{

    private Long id;
    private LocalDateTime reservationStartDate;
    private LocalDateTime reservationEndDate;
    private boolean isActive;
    private User userDto;
    private Book bookDto;

    private Date minExpectedReturnDate;
    private int userPosition;
    private int maxNmbReservation;
//    private CopyDto copyDto;


}
