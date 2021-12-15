package com.emilie.Lib7.Models.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="loan")
@Data
@NoArgsConstructor

public class Loan implements Serializable {

    public static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="loan_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="copy_id", nullable=false)
    private Copy copy;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(name="loan_start_date", nullable=false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date loanStartDate;

    @Column(name="loan_end_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date loanEndDate;

    @Column(name="extended", nullable=false)
    private boolean extended;

    @Column(name="returned", nullable=false)
    private boolean returned;

}
