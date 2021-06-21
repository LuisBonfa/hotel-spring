package com.senior.challenge.user.service;

import com.senior.challenge.user.entity.Role;
import com.senior.challenge.user.entity.User;
import com.senior.challenge.user.entity.UserRole;
import com.senior.challenge.user.repository.UserRoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserRoleServiceTest.class})
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class UserRoleServiceTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserRoleService userRoleService;

    @BeforeEach
    private void setUp(){
        userRoleService = new UserRoleService(userRoleRepository, roleService);
    }

    @Test
    void loadUserRolesFromUserDTO() {
        User user = User.generateUserForTesting("Thiago", "34656312851", "luisbonfire@gmail.com",
                "thiago", "55988914622", "123456789");

        Role role = Role.generateRoleForTesting("admin");
        Role secondRole = Role.generateRoleForTesting("client");

        List<String> roles = new ArrayList<>();
        roles.add("client");
        roles.add("admin");

        when(roleService.findByName(role.getName())).thenReturn(role);
        when(roleService.findByName(secondRole.getName())).thenReturn(secondRole);

        List<UserRole> userRoles = userRoleService.loadRolesForSave(roles, user);

        Assertions.assertThat(userRoles).isNotEmpty();
        Assertions.assertThat(userRoles.size()).isEqualTo(2);
        Assertions.assertThat(userRoles.get(1).getRole().getName()).isEqualTo(role.getName());
    }
}