package com.emilie.Lib10.Models.Dtos;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
public class BookDto implements Serializable {

    public static final long serialVersionUID=1L;

    private Long bookId;
    private String title;
    private String isbn;
    private String summary;
    private AuthorDto authorDto;
    private Set<CopyDto> copyDtos=new HashSet<>(); //instancie le tableau vide
    private List<ReservationDto> reservations = new ArrayList<>();

    private boolean isAvailable;
    private int maxReservation;

    //findable with reservations.size()
 //   private int nmbPendingReservations;
}
