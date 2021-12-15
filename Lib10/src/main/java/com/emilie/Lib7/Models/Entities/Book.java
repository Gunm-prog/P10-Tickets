package com.emilie.Lib7.Models.Entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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

    @Column(name="summary", length=800)
    private String summary;

    @JsonIgnoreProperties("books")
    @ManyToOne
    @JoinColumn(name="author_id", nullable=false)
    private Author author;

    @OneToMany(mappedBy="book")
    private Set<Copy> copies;

}
