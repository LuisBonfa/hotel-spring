package com.senior.challenge.user.controller;


import com.senior.challenge.user.dto.JwtRequest;
import com.senior.challenge.user.dto.JwtResponse;
import com.senior.challenge.user.security.JwtTokenUtil;
import com.senior.challenge.user.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
public class AuthenticationController {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(JwtTokenUtil jwtTokenUtil, AuthenticationService authenticationService, AuthenticationManager authenticationManager) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        return ResponseEntity.ok(authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
    }

    public JwtResponse authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User Disabled", e);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid Credentials", e);
        }

        final var userDetails = authenticationService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new JwtResponse(token, username);
    }
}