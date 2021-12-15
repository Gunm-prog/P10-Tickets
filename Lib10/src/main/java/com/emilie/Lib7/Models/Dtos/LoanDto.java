package com.emilie.Lib7.Models.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class LoanDto implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;
    private Date loanStartDate;
    private Date loanEndDate;
    private boolean Extended;
    private boolean isReturned;
    private UserDto userDto;
    private CopyDto copyDto;

}
