package com.emilie.Lib7.Models.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name="copy")
@EqualsAndHashCode(exclude={"library", "book"})

public class Copy implements Serializable {

    public static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="copy_id")
    private Long id;

    @Column(name="available", nullable=false)
    private boolean available;

    @JsonIgnoreProperties("copies")
    @ManyToOne
    @JoinColumn(name="book_id", nullable=false)
    private Book book;

    @JsonIgnoreProperties("copies")
    @ManyToOne
    @JoinColumn(name="library_id", nullable=false)
    private Library library;

}

