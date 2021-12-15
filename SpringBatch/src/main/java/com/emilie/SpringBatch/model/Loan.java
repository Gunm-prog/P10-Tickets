package com.emilie.SpringBatch.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class Loan implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;
    private Date loanStartDate;
    private Date loanEndDate;
    private boolean isExtended;
    private User userDto;
    private Copy copyDto;

}
