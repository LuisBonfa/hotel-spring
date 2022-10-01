package com.alten.hotel.security.authentication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO used to receive the data
 * to perform a login in the system
 *
 * @author luis.bonfa
 */
@Getter
@Setter
@NoArgsConstructor
public class AuthenticationDTO {
    private String username;
    private String password;
}