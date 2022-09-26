package com.alten.hotel.userrole.entity;

import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.common.persistence.Creatable;
import com.alten.hotel.role.entity.RoleEntity;
import com.alten.hotel.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_role")
public class UserRoleEntity extends Creatable {

    @JsonBackReference
    @NotNull(message = " User is required.")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "user_role_user_fk"))
    private UserEntity user;

    @JsonBackReference
    @NotNull(message = " Role is required.")
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false, foreignKey = @ForeignKey(name = "user_role_role_fk"))
    private RoleEntity role;

    @NotNull(message = "Status is required.")
    @Enumerated(EnumType.STRING)
    private CommonStatus status;

    public UserRoleEntity(RoleEntity roleEntity) {
        this.role = roleEntity;
    }
}
