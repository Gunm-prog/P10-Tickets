package com.emilie.Lib7.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.emilie.Lib7.Models.Entities.UserPrincipal;
import com.emilie.Lib7.Services.impl.UserDetailsServiceImpl;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain)
            throws ServletException, IOException {
        // 1. Check for token in request
        String token=request.getHeader( JwtProperties.HEADER_STRING );

        // 2. If there is no token the user won't be authenticated.
        if (token == null) {
            logger.warn( "token is null" );
            chain.doFilter( request, response );
            return;
        }

        token=token.replace( JwtProperties.TOKEN_PREFIX, "" );


        try {

            // 3. Validate the token
            DecodedJWT jwt=JWT.require( Algorithm.HMAC512( JwtProperties.SECRET.getBytes() ) )
                    .build()
                    .verify( token );

            String username=jwt.getSubject();
            if (username != null) {

                // 4. Retrieve the user into bdd with username
                UserPrincipal userPrincipal=userDetailsServiceImpl.loadUserByUsername( username );


                // 5. Create auth object
                UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(
                        userPrincipal, userPrincipal.getPassword(), userPrincipal.getAuthorities() );

                // 6. Authenticate the user
                SecurityContextHolder.getContext().setAuthentication( auth );
            }

        } catch (Exception e) {
            // In case of failure. Make sure user won't be authenticated
            SecurityContextHolder.clearContext();
        }

        chain.doFilter( request, response );
    }

}
