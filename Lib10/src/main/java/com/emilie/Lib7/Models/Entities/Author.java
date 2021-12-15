package com.emilie.Lib7.Models.Entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name="author")
@EqualsAndHashCode(exclude="books")
public class Author implements Serializable {

    public static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="author_id")
    private Long authorId;

    @NotNull
    @Column(name="first_name")
    private String firstName;

    @NotNull
    @Column(name="last_name")
    private String lastName;


    @OneToMany(mappedBy="author")
    @JsonIgnoreProperties("authors")
    private Set<Book> books;

}
