package com.emilie.Lib7.Services.impl;

import com.emilie.Lib7.Models.Entities.User;
import com.emilie.Lib7.Models.Entities.UserPrincipal;
import com.emilie.Lib7.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userAuthenticationRepository) {
        this.userRepository=userAuthenticationRepository;

    }


    @Override
    public UserPrincipal loadUserByUsername(String email) {
        Optional<User> userJwt=userRepository.findByEmail( email );
        if (userJwt.isEmpty()) {
            throw new UsernameNotFoundException( "No user found for " + email + "." );
        }
        return new UserPrincipal( userJwt.get() );
    }

    public User save(User user) {
        return this.userRepository.save( user );
    }


}

