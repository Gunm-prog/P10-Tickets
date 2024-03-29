package com.emilie.Lib10.Config.Security;

import com.emilie.Lib10.Config.Jwt.JwtAuthenticationEntryPoint;
import com.emilie.Lib10.Config.Jwt.JwtProperties;
import com.emilie.Lib10.Config.Jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService( jwtUserDetailsService ).passwordEncoder( passwordEncoder() );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity.csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests().antMatchers( "/authenticate" ).permitAll()
                .and().authorizeRequests().antMatchers( "/register/customer" ).permitAll()
                .and().authorizeRequests().antMatchers( "/api/v1/copies/search" ).permitAll()
                .and().authorizeRequests().antMatchers( "/api/v1/libraries/{id}" ).permitAll()
                .and().authorizeRequests().antMatchers( "/api/v1/books/{id}" ).permitAll()
                .and().authorizeRequests().antMatchers( "/api/v1/reservations/*" ).authenticated()
                /*.and()*/
                /*.authorizeRequests().antMatchers("/api/v1/users/createUserAccount").permitAll()*/
                .antMatchers( "/api/v1/libraries/newLibrary" ).hasAnyRole( JwtProperties.ROLE_ADMIN)
                .antMatchers( "/api/v1/libraries/updateLibrary" ).hasAnyRole( JwtProperties.ROLE_ADMIN)
                .antMatchers( "/api/v1/libraries/delete/{id}" ).hasAnyRole( JwtProperties.ROLE_ADMIN)

                .antMatchers( "/api/v1/authors/**" ).hasAnyRole( JwtProperties.ROLE_ADMIN, JwtProperties.ROLE_EMPLOYEE )

                .antMatchers( "/register/employee" ).hasAnyRole( JwtProperties.ROLE_ADMIN, JwtProperties.ROLE_EMPLOYEE )
                /*.antMatchers("/edit/**").hasRole(JwtProperties.ROLE_TECHNICAL)*/
                .antMatchers( "/api/v1/delete/**" ).hasAnyRole( JwtProperties.ROLE_ADMIN, JwtProperties.ROLE_EMPLOYEE )
                .antMatchers( "/api/v1/loans/return/{id}" ).hasAnyRole( JwtProperties.ROLE_ADMIN, JwtProperties.ROLE_EMPLOYEE )
                .antMatchers( "/api/v1/loans/send/{id}" ).hasAnyRole( JwtProperties.ROLE_ADMIN, JwtProperties.ROLE_EMPLOYEE )
             //   .antMatchers( "/api/v1/loans/sendRecoveryMails/{id}" ).hasAnyRole( JwtProperties.ROLE_ADMIN, JwtProperties.ROLE_EMPLOYEE )


                // all other requests need to be authenticated
                .anyRequest().authenticated().and().
                // make sure we use stateless session; session won't be used to
                // store user's state.
                        exceptionHandling().authenticationEntryPoint( jwtAuthenticationEntryPoint ).and().sessionManagement()
                .sessionCreationPolicy( SessionCreationPolicy.STATELESS );

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore( jwtRequestFilter, UsernamePasswordAuthenticationFilter.class );
    }
}
