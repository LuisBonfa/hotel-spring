package com.alten.hotel.security.authentication.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO used to receive the data
 * to perform a login in the system
 *
 * @author luis.bonfa
 */
@Getter
@Setter
@Builder
public class JwtResponse {
    private String bearer;
}