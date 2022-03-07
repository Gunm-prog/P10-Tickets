package com.emilie.Lib10WebClient.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class Book {

    private Long bookId;
    private String title;
    private String isbn;
    private String summary;
    private Author authorDto;
    private Set<Copy> copies= new HashSet<>();
    private List<Library> libraries = new ArrayList<>();
    private List<Copy> copyDtos = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    private boolean isAvailable;
    private int maxReservation;
    private boolean reservedByCurrentUser;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + bookId +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", isAvailable='" + isAvailable + '\'' +
                ", maxReservation='" + maxReservation + '\'' +
                ", summary='" + summary + '\'' +
                ", author=" + authorDto +
                ", copies=" + copies +
                '}';
    }
}
