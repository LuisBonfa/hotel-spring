package com.alten.hotel.user.mapper;

import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.common.mapper.Mapper;
import com.alten.hotel.common.utils.Security;
import com.alten.hotel.user.dto.UserDTO;
import com.alten.hotel.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToEntityMapper implements Mapper<UserDTO, UserEntity> {

    @Override
    public UserEntity mapNonNull(UserDTO dto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(dto.getName());
        userEntity.setAlias(dto.getAlias());
        userEntity.setUsername(dto.getUsername());
        userEntity.setPassword(Security.hash(dto.getPassword()));
        userEntity.setEmail(dto.getEmail());
        userEntity.setDocument(dto.getDocument());
        userEntity.setPhone(dto.getPhone());
        userEntity.setStatus(CommonStatus.ACTIVE);
        userEntity.setTries(0);
        return userEntity;
    }
}
