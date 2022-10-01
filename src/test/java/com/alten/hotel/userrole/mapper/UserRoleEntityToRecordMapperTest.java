package com.alten.hotel.userrole.mapper;

import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.role.entity.RoleEntity;
import com.alten.hotel.user.entity.UserEntity;
import com.alten.hotel.userrole.dto.UserRoleRecord;
import com.alten.hotel.userrole.entity.UserRoleEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserRoleEntityToRecordMapperTest {

    @Spy
    UserRoleEntityToRecordMapper userRoleEntityToRecordMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void mapNonNullTest() {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        userEntity.setName("USER");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(UUID.randomUUID());
        roleEntity.setName("ROLE");

        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setUser(userEntity);
        userRole.setRole(roleEntity);
        userRole.setStatus(CommonStatus.ACTIVE);

        UserRoleRecord userRoleRecord = userRoleEntityToRecordMapper.mapNonNull(userRole);

        Assertions.assertThat("USER").isEqualTo(userRoleRecord.getUser());
        Assertions.assertThat("ROLE").isEqualTo(userRoleRecord.getRole());
        Assertions.assertThat(CommonStatus.ACTIVE).isEqualTo(userRoleRecord.getStatus());
    }
}