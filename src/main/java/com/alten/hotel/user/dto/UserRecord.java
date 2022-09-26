package com.alten.hotel.user.dto;

import com.alten.hotel.common.enums.CommonStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

/**
 * Used to answer and show to the user information
 * about the bookings
 *
 * @author luis.bonfa
 */
@Getter
@Builder
public class UserRecord {

    private UUID id;
    private String name;
    private String username;
    private String alias;
    private String email;
    private String phone;
    private String document;
    private List<String> roles;
    private CommonStatus status;
}
