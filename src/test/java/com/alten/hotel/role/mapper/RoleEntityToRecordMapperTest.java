package com.alten.hotel.role.mapper;

import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.role.dto.RoleRecord;
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

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class RoleEntityToRecordMapperTest {

    @Spy
    RoleEntityToRecordMapper roleRecordMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void mapNonNullTest() {

        RoleEntity role = new RoleEntity();
        role.setId(UUID.randomUUID());
        role.setName("roleTest");
        role.setStatus(CommonStatus.ACTIVE);

        RoleRecord roleRecord = roleRecordMapper.mapNonNull(role);

        Assertions.assertThat(role.getName()).isEqualTo(roleRecord.getName());
        Assertions.assertThat(role.getStatus()).isEqualTo(roleRecord.getStatus());
    }
}