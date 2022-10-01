package com.alten.hotel.userrole.mapper;

import com.alten.hotel.role.entity.RoleEntity;
import com.alten.hotel.user.entity.UserEntity;
import com.alten.hotel.userrole.dto.UserRoleDTO;
import com.alten.hotel.userrole.entity.UserRoleEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserRoleDtoToEntityMapperTest {

    @Spy
    @InjectMocks
    UserRoleDtoToEntityMapper userRoleDtoToEntityMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void mapNonNullTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(UUID.randomUUID());

        UserRoleDTO dto = new UserRoleDTO();
        dto.setUserId(userEntity.getId());
        dto.setRoleId(roleEntity.getId());

        UserRoleEntity userRoleEntity = userRoleDtoToEntityMapper.mapNonNull(dto);

        Assertions.assertThat(userEntity.getId()).isEqualTo(userRoleEntity.getUser().getId());
        Assertions.assertThat(roleEntity.getId()).isEqualTo(userRoleEntity.getRole().getId());
    }
}