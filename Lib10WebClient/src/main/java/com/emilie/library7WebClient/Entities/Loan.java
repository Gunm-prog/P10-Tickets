package com.emilie.library7WebClient.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Loan {

    private Long id;
    private Date loanStartDate;
    private Date loanEndDate;
    private boolean extended;
    private boolean returned;
    private Copy copyDto;
    private User user;
}
