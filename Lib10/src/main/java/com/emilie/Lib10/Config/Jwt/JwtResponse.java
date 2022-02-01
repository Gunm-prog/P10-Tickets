package com.emilie.Lib10.Config.Jwt;


import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID=-8091879091924046844L;
    private final String jwtToken;

    public JwtResponse(String jwtToken) {
        this.jwtToken=jwtToken;
    }

    public String getToken() {
        return this.jwtToken;
    }
}