package com.alten.hotel.userrole.mapper;

import com.alten.hotel.common.mapper.Mapper;
import com.alten.hotel.userrole.dto.UserRoleRecord;
import com.alten.hotel.userrole.entity.UserRoleEntity;
import org.springframework.stereotype.Component;

@Component
public class UserRoleEntityToRecordMapper implements Mapper<UserRoleEntity, UserRoleRecord> {

    @Override
    public UserRoleRecord mapNonNull(UserRoleEntity userRole) {
        return UserRoleRecord.builder()
                .role(userRole.getRole().getName())
                .user(userRole.getUser().getAlias())
                .status(userRole.getStatus())
                .build();
    }
}
