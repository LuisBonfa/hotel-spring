package com.alten.hotel.role.entity;

import com.alten.hotel.booking.repository.BookingRepository;
import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.common.persistence.Creatable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity used to store information
 * about user roles.
 * <p>
 * Repository: {@link BookingRepository}
 *
 * @author luis.bonfa
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "role")
public class RoleEntity extends Creatable {

    @NotNull(message = "Name is required")
    private String name;
    
    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private CommonStatus status;
}
