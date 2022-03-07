package com.emilie.Lib10.Models.Entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="reservation")
@Data
@NoArgsConstructor
public class Reservation implements Serializable {

    public static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="reservation_id")
    private Long id;

    @JsonIgnoreProperties("reservations")
    @ManyToOne
    @JoinColumn(name="book_id", nullable=false)
    private Book book;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(name="start_date", nullable=false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime reservationStartDate;

    @Column(name="end_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:MM:SS")
    private LocalDateTime reservationEndDate;

    @Column(name="isActive", nullable=false)
    private boolean isActive;
}
