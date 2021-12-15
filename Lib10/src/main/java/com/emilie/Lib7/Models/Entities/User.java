package com.emilie.Lib7.Models.Entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author emilie
 */
@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Long id;


    @Column(name="password", nullable=false)
    private String password;

    @Column(name="active")
    private boolean active;

    @Column(name="roles", nullable=false)
    private String roles;


    @Column(name="last_name", nullable=false)
    private String lastName;

    @Column(name="first_name", nullable=false)
    private String firstName;

    @Column(name="email", length=50, unique=true, nullable=false)
    private String email;

    @OneToMany(mappedBy="user")
    private Set<Loan> loans;

    @Embedded
    private Address address;


    public String getUsername() {
        return email;
    }

    public List<String> getRolesList() {
        if (this.roles.length() > 0) {
            return Arrays.asList( this.roles.split( "," ) );
        }
        return new ArrayList<>();
    }


}

