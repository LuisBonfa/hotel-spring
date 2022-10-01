package com.alten.hotel.user.mapper;

import com.alten.hotel.common.mapper.Mapper;
import com.alten.hotel.user.dto.UserRecord;
import com.alten.hotel.user.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserEntityToRecordMapper implements Mapper<UserEntity, UserRecord> {

    @Override
    public UserRecord mapNonNull(UserEntity user) {
        return UserRecord.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .alias(user.getAlias())
                .email(user.getEmail())
                .document(user.getDocument())
                .phone(user.getPhone())
                .status(user.getStatus())
                .roles(user.getUserRoles().stream().map(userRoleEntity -> userRoleEntity.getRole().getName()).collect(Collectors.toList()))
                .build();
    }
}
