package com.alten.hotel.userrole.dto;

import com.alten.hotel.common.enums.CommonStatus;
import lombok.Builder;
import lombok.Getter;

/**
 * Used to answer and show to the user, information
 * about the roles that the user has
 *
 * @author luis.bonfa
 */
@Getter
@Builder
public class UserRoleRecord {
    private String user;
    private String role;
    private CommonStatus status;
}
