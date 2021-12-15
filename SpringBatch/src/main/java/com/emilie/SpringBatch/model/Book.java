package com.emilie.SpringBatch.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class Book implements Serializable {

    public static final long serialVersionUID=1L;

    private Long bookId;
    private String title;
    private String isbn;

}
