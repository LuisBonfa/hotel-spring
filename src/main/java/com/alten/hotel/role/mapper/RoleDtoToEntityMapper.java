package com.alten.hotel.role.mapper;

import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.common.mapper.Mapper;
import com.alten.hotel.role.dto.RoleDTO;
import com.alten.hotel.role.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleDtoToEntityMapper implements Mapper<RoleDTO, RoleEntity> {

    @Override
    public RoleEntity mapNonNull(RoleDTO dto) {
        RoleEntity role = new RoleEntity();
        role.setName(dto.getName());
        role.setStatus(CommonStatus.ACTIVE);
        return role;
    }
}
