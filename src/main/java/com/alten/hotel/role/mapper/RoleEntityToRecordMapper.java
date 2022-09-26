package com.alten.hotel.role.mapper;

import com.alten.hotel.common.mapper.Mapper;
import com.alten.hotel.role.dto.RoleRecord;
import com.alten.hotel.role.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleEntityToRecordMapper implements Mapper<RoleEntity, RoleRecord> {

    @Override
    public RoleRecord mapNonNull(RoleEntity role) {
        return RoleRecord.builder()
                .name(role.getName())
                .status(role.getStatus())
                .build();
    }
}
