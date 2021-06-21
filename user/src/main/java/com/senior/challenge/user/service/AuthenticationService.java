package com.senior.challenge.user.service;

import com.senior.challenge.user.entity.User;
import com.senior.challenge.user.repository.UserRepository;
import com.senior.challenge.user.security.JwtTokenUtil;
import com.senior.challenge.user.utils.Security;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(JwtTokenUtil jwtTokenUtil, UserRepository userRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) {
        try {
            Optional<User> user = userRepository.findByName(name);
            if (user.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

            return new org.springframework.security.core.userdetails.User(user.get().getName(), user.get().getPassword(),
                    new ArrayList<>());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading User", e);
        }
    }

    public Authentication verifyUserCredentials(String name, String password) {
        //Validate if the user exists in the database and verify
        // if the user password is correct
        try {
            Optional<User> user = userRepository.findByName(name);
            boolean passwordVerify = Security.validate(password, user.get().getPassword());
            if (!passwordVerify)
                throw new NotFoundException("User Not Found");

            return new UsernamePasswordAuthenticationToken(name, password);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Verifying your Identity", e);
        }
    }
}
