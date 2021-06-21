package com.senior.challenge.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.senior.challenge.user.persistence.Creatable;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name="user_role")
public class UserRole extends Creatable {

    @JsonBackReference
    @NotNull(message = " Usuário é Obrigatório.")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "user_role_fk"))
    private User user;

    @JsonBackReference
    @NotNull(message = " Role é Obrigatório.")
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false, foreignKey = @ForeignKey(name = "role_role_fk"))
    private Role role;

}
