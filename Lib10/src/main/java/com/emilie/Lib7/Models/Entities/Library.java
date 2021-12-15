package com.emilie.Lib7.Models.Entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="library")
@Data
@NoArgsConstructor
public class Library implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="library_id")
    private Long libraryId;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="phoneNumber", length=10, nullable=false)
    private String phoneNumber;

    @Embedded
    private Address address;


    @OneToMany(mappedBy="library")
    @JsonIgnoreProperties("libraries")
    private Set<Copy> copies;
}





