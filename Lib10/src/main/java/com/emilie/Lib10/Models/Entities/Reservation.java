package com.emilie.Lib10.Models.Entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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

    @ManyToOne
    @JoinColumn(name="book_id", nullable=false)
    private Book book;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    /*@ManyToOne
    @JoinColumn(name="copy_id", nullable=false)
    private Copy copy;*/

    @Column(name="start_date", nullable=false)
    @JsonFormat(pattern="yyyy-MM-dd HH:MM:SS")
    private LocalDateTime reservationStartDate;

    @Column(name="end_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:MM:SS")
    private LocalDateTime reservationEndDate;

    @Column(name="isActive", nullable=false)
    private boolean isActive;


}
