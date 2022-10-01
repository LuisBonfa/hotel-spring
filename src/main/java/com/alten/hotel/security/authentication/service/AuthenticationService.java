package com.alten.hotel.security.authentication.service;

import com.alten.hotel.common.utils.Security;
import com.alten.hotel.security.authentication.dto.JwtResponse;
import com.alten.hotel.security.authorization.bean.UserDetailsImpl;
import com.alten.hotel.security.authorization.util.JwtTokenUtil;
import com.alten.hotel.user.entity.UserEntity;
import com.alten.hotel.user.service.UserService;
import javassist.NotFoundException;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationService implements UserDetailsService {

    JwtTokenUtil jwtTokenUtil;
    AuthenticationManager authenticationManager;
    UserService userService;

    public JwtResponse authenticate(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User Disabled", e);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid Credentials", e);
        }

        UserDetails userDetails = loadUserByUsername(email);
        return JwtResponse.builder().bearer(jwtTokenUtil.generateToken(userDetails)).build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        try {
            UserEntity user = userService.findByEmail(email);
            return new UserDetailsImpl(user);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading User", e);
        }
    }

    public Authentication verifyUserCredentials(String email, String password) {
        try {
            UserEntity user = userService.findByEmail(email);
            boolean passwordVerify = Security.validate(password, user.getPassword());
            if (!passwordVerify) {
                throw new NotFoundException("User Not Found");
            }

            return new UsernamePasswordAuthenticationToken(email, password);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Verifying your Identity", e);
        }
    }
}
