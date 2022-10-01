package com.alten.hotel.role.repository;

import com.alten.hotel.role.entity.RoleEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void findByNamesTest() {
        List<String> names = List.of("admin", "user");
        Set<RoleEntity> roles = roleRepository.findByNameIn(names);

        Assertions.assertThat(2).isEqualTo(roles.size());
    }
}