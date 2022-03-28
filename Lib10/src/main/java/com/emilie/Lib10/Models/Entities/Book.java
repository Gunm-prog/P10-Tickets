package com.emilie.Lib10.Models.Entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Entity
@Table(name="book")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude="author")
public class Book implements Serializable {

    public static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long bookId;

    @Column(name="title", length=50, nullable=false)
    private String title;

    @Column(name="isbn", length=30, nullable=false)
    private String isbn;

    @Column(name="summary", length=1000)
    private String summary;

    @ManyToOne
    @JoinColumn(name="author_id", nullable=false)
    @JsonIgnoreProperties("books")
    private Author author;

    @OneToMany(mappedBy="book")
    private Set<Copy> copies;

    @OneToMany(mappedBy="book")
    private List<Reservation> reservationList;

    @Override
    public String toString() {

        AtomicReference<String> copyList =new AtomicReference<>( "{" );
        copies.forEach( copy -> {
            copyList.set( copyList + ", " + copy.getId() );
        } );
        copyList.set("}");

        AtomicReference<String> rsvList = new AtomicReference<>("{");
        reservationList.forEach( reservation -> {
            rsvList.set( rsvList + ", " + reservation.getId() );
        } );
        rsvList.set("}");

        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", summary='" + summary + '\'' +
                ", author=" + author.getAuthorId() +
                ", copies=" + copyList +
                ", reservationList=" + rsvList +
                '}';
    }
}
