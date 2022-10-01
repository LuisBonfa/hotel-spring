package com.alten.hotel.security.authentication.controller;


import com.alten.hotel.security.authentication.dto.AuthenticationDTO;
import com.alten.hotel.security.authentication.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationDTO authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
    }
}