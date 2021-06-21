package com.senior.challenge.user.service;

import com.senior.challenge.user.entity.Role;
import com.senior.challenge.user.entity.User;
import com.senior.challenge.user.entity.UserRole;
import com.senior.challenge.user.repository.RoleRepository;
import com.senior.challenge.user.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final RoleService roleService;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository, RoleService roleService) {
        this.userRoleRepository = userRoleRepository;
        this.roleService = roleService;
    }

    public UserRole save(UserRole userRole) {
        try {
            userRole.setCreationDate();
            userRoleRepository.save(userRole);

            return userRole;

        } catch (PersistenceException pe) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving UserRole", pe);
        }
    }


    public List<UserRole> loadRolesForSave(List<String> roles, User user) {
        try {
            List<UserRole> userRoles = new ArrayList<>();
            for (String name : roles) {
                Role role = roleService.findByName(name);

                UserRole userRole = new UserRole();
                userRole.setCreationDate();
                userRole.setRole(role);
                userRole.setUser(user);
                userRole.setCreationDate();
                userRoles.add(userRole);
            }

            return userRoles;

        } catch (PersistenceException pe) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading roles from data", pe);
        }
    }
}
