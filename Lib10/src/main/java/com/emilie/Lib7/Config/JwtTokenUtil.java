package com.emilie.Lib7.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.emilie.Lib7.Models.Entities.UserPrincipal;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID=-2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY=5 * 60 * 60;


    //generate token for user
    public String generateToken(UserPrincipal userPrincipal) {

        return doGenerateToken( userPrincipal );
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(UserPrincipal userPrincipal) {
        return JWT.create()
                .withClaim( "role", userPrincipal.getAuthorities().toString() )
                .withClaim( "userId", userPrincipal.getUserId() )
                .withClaim( "lastname", userPrincipal.getLastname() )
                .withClaim( "firstname", userPrincipal.getFirstname() )
                .withSubject( userPrincipal.getUsername() )
                .withIssuedAt( new Date( System.currentTimeMillis() ) )
                .withExpiresAt( new Date( System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000 ) )
                .sign( Algorithm.HMAC512( JwtProperties.SECRET.getBytes() ) );
    }

}