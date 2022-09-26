package com.alten.hotel.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO used to UPDATE and INSERT
 * new users
 *
 * @author luis.bonfa
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    @NotNull(message = "Name is required")
    private String name;
    private String username;
    private String password;
    private String alias;
    private String email;
    private String phone;
    private String document;
    private List<String> roles;
}
