package com.emilie.SpringBatch.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Copy implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;
    private boolean available;
    private Book bookDto;


}

