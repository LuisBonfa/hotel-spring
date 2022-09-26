package com.alten.hotel.role.dto;

import com.alten.hotel.common.enums.CommonStatus;
import lombok.Builder;
import lombok.Getter;

/**
 * Used to answer and show to the user information
 * about the user roles
 *
 * @author luis.bonfa
 */
@Getter
@Builder
public class RoleRecord {
    private String name;
    private CommonStatus status;
}
