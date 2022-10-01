package com.alten.hotel.role.mapper;

import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.role.dto.RoleDTO;
import com.alten.hotel.role.entity.RoleEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class RoleDtoToEntityMapperTest {

    @Spy
    RoleDtoToEntityMapper roleDtoToEntityMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void mapNonNullTest() {
        RoleDTO dto = new RoleDTO();
        dto.setName("roleTest");

        RoleEntity roleEntityResult = roleDtoToEntityMapper.mapNonNull(dto);

        Assertions.assertThat("roleTest").isEqualTo(roleEntityResult.getName());
        Assertions.assertThat(CommonStatus.ACTIVE).isEqualTo(roleEntityResult.getStatus());
    }
}