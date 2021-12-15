package com.emilie.Lib7.Models.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name="category")
public class Category implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @Column(name="category_id")
    private Long categoryId;

    @Column(name="label")
    private String label;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="book_id", referencedColumnName="id")
    private Book book;

}
