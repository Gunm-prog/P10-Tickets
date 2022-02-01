package com.emilie.Lib10.Models.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;



@Data
@NoArgsConstructor
public class ReservationDto implements Serializable{

    private static final long serialVersionUID=1L;

    private Long id;
    private LocalDateTime reservationStartDate;
    private LocalDateTime reservationEndDate;
    private boolean isActive;
    private UserDto userDto;
    private BookDto bookDto;

    private Date minExpectedReturnDate;
    private int nmbReservation;
    private int userPosition;
}
