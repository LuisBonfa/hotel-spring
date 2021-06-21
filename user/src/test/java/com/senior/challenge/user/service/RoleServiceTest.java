package com.senior.challenge.user.service;

import com.senior.challenge.user.entity.Role;
import com.senior.challenge.user.repository.RoleRepository;
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
import java.util.Optional;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {RoleServiceTest.class})
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    private void setUp(){
        roleService = new RoleService(roleRepository);
    }

    @Test
    void saveRoleTest() {
        Role role = Role.generateRoleForTesting("admin");
        Role newRole = roleService.save(role);

        verify(roleRepository, times(1)).save(role);

        Assertions.assertThat(newRole).isNotNull();
        Assertions.assertThat(newRole.getName()).isEqualTo(role.getName());
    }

    @Test
    void findRoleByNameTest() {
        Role role = Role.generateRoleForTesting("admin");
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));

        Role expectedRole = roleService.findByName("admin");
        verify(roleRepository, times(1)).findByName(role.getName());
        Assertions.assertThat(expectedRole).isNotNull();
        Assertions.assertThat(expectedRole.getName()).isEqualTo(role.getName());
    }

    @Test
    void findAllRolesTest() {
        Role role = Role.generateRoleForTesting("admin");
        Role secondRole = Role.generateRoleForTesting("client");

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        roles.add(secondRole);

        when(roleRepository.findAll()).thenReturn(roles);
        List<Role> expectedRoles = roleService.findAll();

        verify(roleRepository, times(1)).findAll();
        Assertions.assertThat(expectedRoles).isNotNull();
        Assertions.assertThat(expectedRoles.size()).isEqualTo(2);
        Assertions.assertThat(expectedRoles.get(0).getName()).isEqualTo(role.getName());

    }
}