package com.alten.hotel.userrole.mapper;

import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.common.mapper.Mapper;
import com.alten.hotel.role.entity.RoleEntity;
import com.alten.hotel.user.entity.UserEntity;
import com.alten.hotel.userrole.dto.UserRoleDTO;
import com.alten.hotel.userrole.entity.UserRoleEntity;
import org.springframework.stereotype.Component;

@Component
public class UserRoleDtoToEntityMapper implements Mapper<UserRoleDTO, UserRoleEntity> {
    
    @Override
    public UserRoleEntity mapNonNull(UserRoleDTO dto) {
        UserRoleEntity userRole = new UserRoleEntity();

        UserEntity user = new UserEntity();
        user.setId(dto.getUserId());

        RoleEntity role = new RoleEntity();
        role.setId(dto.getRoleId());

        userRole.setUser(user);
        userRole.setRole(role);
        userRole.setStatus(CommonStatus.ACTIVE);
        return userRole;
    }
}
