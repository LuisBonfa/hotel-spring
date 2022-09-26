package com.alten.hotel.userrole.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * DTO used to UPDATE and INSERT
 * new roles into users
 *
 * @author luis.bonfa
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDTO {
    private UUID userId;
    private UUID roleId;
}
