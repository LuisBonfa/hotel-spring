package com.senior.challenge.user.service;

import com.senior.challenge.user.entity.Role;
import com.senior.challenge.user.repository.RoleRepository;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(Role newRole) {
        try {
            var role = roleRepository.findByName(newRole.getName());

            if (role.isPresent())
                throw new ResponseStatusException(HttpStatus.FOUND, "Role already registered");

            roleRepository.save(newRole);

            return newRole;

        } catch (PersistenceException pe) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating role", pe);
        }
    }

    public Role findByName(String name) {
        try {
            Optional<Role> role = roleRepository.findByName(name);

            if (role.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role Not Found");

            return role.get();

        } catch (PersistenceException pe) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading role", pe);
        }
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

}
