package com.emilie.Lib10WebClient.Security;

import org.springframework.web.util.WebUtils;
import com.auth0.jwt.JWT;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtTokenUtils {

    public static final String HEADER="access_token";


    private JwtTokenUtils() {
    }

    /**
     * This method generate a cookie with token as value
     *
     * @param token access token to store
     * @return a cookie
     */
    public static Cookie generateCookie(String token) {

        Cookie cookie=new Cookie( JwtProperties.HEADER, token );
        cookie.setSecure( false );
        cookie.setHttpOnly( true );
        cookie.setMaxAge( 999999 ); // 12 days
        cookie.setDomain( "localhost" );
        cookie.setPath( "/" );

        return cookie;
    }

    /**
     * This method delete cookie stored
     *
     * @param httpServletResponse the HttpServletResponse with cookie to delete
     */
    public static void clear(HttpServletResponse httpServletResponse) {
        Cookie cookie=new Cookie( JwtProperties.HEADER, null );
        cookie.setPath( "/" );
        cookie.setHttpOnly( true );
        cookie.setMaxAge( 0 );
        httpServletResponse.addCookie( cookie );
    }

    /**
     * return a field form the cookie with the name of his key
     *
     * @param httpServletRequest HttpServletResponse with cookie to use
     * @param name               the name of the value to find
     */
    public static String getValue(HttpServletRequest httpServletRequest, String name) {
        Cookie cookie=WebUtils.getCookie( httpServletRequest, name );
        return cookie != null ? cookie.getValue() : null;
    }

    public static String getUsernameFromJWT(String token) {
        return JWT.decode( token ).getSubject();
    }

    public static Integer getUserIdFromJWT(String token) {
        Integer userId=JWT.decode( token ).getClaim( "userId" ).asInt();
        return userId;
    }

    public static String getFirstnameFromJWT(String token) {
        return JWT.decode( token ).getClaim( "firstname" ).asString();
    }

    public static String getLastnameFromJWT(String token) {
        return JWT.decode( token ).getClaim( "lastname" ).asString();
    }

}

