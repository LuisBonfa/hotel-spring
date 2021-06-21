package com.senior.challenge.user.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.senior.challenge.user.dto.RoleDTO;
import com.senior.challenge.user.persistence.Creatable;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "role")
public class Role extends Creatable {

    @NotNull(message = "Nome do cargo é obrigatório")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Status é obrigatório")
    @Column(nullable = false)
    private String status;

    @JsonManagedReference
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

    public static Role create(RoleDTO roleDTO) {
        return new ModelMapper().map(roleDTO, Role.class);
    }

    public static Role generateRoleForTesting(String name) {
        Role role = new Role();
        role.setId(UUID.randomUUID());
        role.setName(name);
        role.setCreationDate();
        role.setStatus("enabled");

        return role;
    }
}
