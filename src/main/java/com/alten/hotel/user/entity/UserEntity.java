package com.alten.hotel.user.entity;

import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.common.persistence.Updatable;
import com.alten.hotel.userrole.entity.UserRoleEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "`user`")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends Updatable {

    @NotNull(message = "Name is required")
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull(message = "Username is required")
    @Column(unique = true, nullable = false)
    private String username;

    @NotNull(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "Alias is required")
    @Column(nullable = false)
    private String alias;

    @Column(nullable = false)
    private Integer tries;

    @NotNull(message = "E-mail is required")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull(message = "Phone is required")
    @Column(unique = true, nullable = false)
    private String phone;

    @NotNull(message = "Document is required")
    @Column(unique = true, nullable = false)
    private String document;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommonStatus status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRoleEntity> userRoles;

    @JsonBackReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookingEntity> userBookings;
}
