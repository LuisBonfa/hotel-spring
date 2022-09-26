package com.alten.hotel.userrole.service;

import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.common.exception.exceptions.GenericException;
import com.alten.hotel.common.exception.exceptions.NotFoundException;
import com.alten.hotel.common.mapper.Mapper;
import com.alten.hotel.common.service.DefaultService;
import com.alten.hotel.role.entity.RoleEntity;
import com.alten.hotel.user.entity.UserEntity;
import com.alten.hotel.userrole.dto.UserRoleDTO;
import com.alten.hotel.userrole.dto.UserRoleRecord;
import com.alten.hotel.userrole.entity.UserRoleEntity;
import com.alten.hotel.userrole.repository.UserRoleRepository;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRoleService implements DefaultService<UserRoleDTO, UserRoleRecord> {

    UserRoleRepository userRoleRepository;
    Mapper<UserRoleEntity, UserRoleRecord> userRoleRecordMapper;
    Mapper<UserRoleDTO, UserRoleEntity> userRoleEntityMapper;

    @Override
    public UserRoleRecord save(UserRoleDTO data) {
        UserRoleEntity userRole = userRoleEntityMapper.mapNonNull(data);
        return userRoleRecordMapper.mapNonNull(userRoleRepository.save(userRole));
    }

    @Override
    public UserRoleRecord update(UUID id, UserRoleDTO data) {
        try {
            UserEntity user = new UserEntity();
            user.setId(data.getUserId());

            RoleEntity role = new RoleEntity();
            role.setId(data.getRoleId());

            UserRoleEntity userRole = loadUserRole(id);
            userRole.setRole(role);
            userRole.setUser(user);

            return userRoleRecordMapper.mapNonNull(userRoleRepository.save(userRole));
        } catch (PersistenceException pe) {
            throw new GenericException(pe);
        }
    }

    @Override
    public List<UserRoleRecord> findAll() {
        return userRoleRepository.findAll().stream().map(userRoleRecordMapper::mapNonNull).collect(Collectors.toList());
    }

    @Override
    public UserRoleRecord findById(UUID id) {
        return userRoleRecordMapper.mapNonNull(loadUserRole(id));
    }

    @Override
    public UserRoleRecord delete(UUID id) {
        UserRoleEntity userRole = loadUserRole(id);
        userRole.setStatus(CommonStatus.UNACTIVE);
        return userRoleRecordMapper.mapNonNull(userRoleRepository.save(userRole));
    }

    private UserRoleEntity loadUserRole(UUID id) {
        Optional<UserRoleEntity> optionalUserRole = userRoleRepository.findById(id);
        if (optionalUserRole.isPresent()) {
            return optionalUserRole.get();
        }
        throw new NotFoundException("User role with this id not found!");
    }
}
