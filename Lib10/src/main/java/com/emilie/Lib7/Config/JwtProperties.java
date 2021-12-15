package com.emilie.Lib7.Config;

public class JwtProperties {


    public static final String SECRET="SecretKeyJWTs";
    public static final long EXPIRATION_TIME=864_000_000; // 10 days
    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authorization";


    /* Roles */
    public static final String ROLE_ADMIN="ADMIN";
    public static final String ROLE_CUSTOMER="CUSTOMER";
    public static final String ROLE_EMPLOYEE="EMPLOYEE";

}
