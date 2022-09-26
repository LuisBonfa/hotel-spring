package com.alten.hotel.user.service;

import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.common.exception.exceptions.GenericException;
import com.alten.hotel.common.exception.exceptions.NotFoundException;
import com.alten.hotel.common.mapper.Mapper;
import com.alten.hotel.common.service.DefaultService;
import com.alten.hotel.common.utils.Security;
import com.alten.hotel.role.entity.RoleEntity;
import com.alten.hotel.role.service.RoleService;
import com.alten.hotel.user.dto.UserDTO;
import com.alten.hotel.user.dto.UserRecord;
import com.alten.hotel.user.entity.UserEntity;
import com.alten.hotel.user.repository.UserRepository;
import com.alten.hotel.userrole.entity.UserRoleEntity;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService implements DefaultService<UserDTO, UserRecord> {

    UserRepository userRepository;
    Mapper<UserDTO, UserEntity> userEntityMapper;
    Mapper<UserEntity, UserRecord> userRecordMapper;
    RoleService roleService;

    @Override
    public UserRecord save(UserDTO data) {
        try {
            List<RoleEntity> roles = roleService.findByNameIn(data.getRoles());
            UserEntity user = userEntityMapper.mapNonNull(data);
            List<UserRoleEntity> userRoles = new ArrayList<>();
            roles.forEach(role -> {
                UserRoleEntity userRoleEntity = new UserRoleEntity();
                userRoleEntity.setUser(user);
                userRoleEntity.setRole(role);
                userRoleEntity.setStatus(CommonStatus.ACTIVE);
                userRoles.add(userRoleEntity);
            });
            user.setUserRoles(userRoles);
            return userRecordMapper.mapNonNull(userRepository.save(user));
        } catch (PersistenceException pe) {
            throw new GenericException(pe);
        }
    }

    @Override
    public UserRecord update(UUID id, UserDTO dto) {
        try {
            UserEntity userEntiy = loadUser(id);
            if (dto.getPassword() != null) {
                userEntiy.setPassword(Security.hash(dto.getPassword()));
            }
            return userRecordMapper.mapNonNull(userRepository.save(userEntiy));
        } catch (PersistenceException pe) {
            throw new GenericException(pe);
        }
    }

    @Override
    public List<UserRecord> findAll() {
        return userRepository.findAll().stream().map(userRecordMapper::mapNonNull).collect(Collectors.toList());
    }

    @Override
    public UserRecord findById(UUID id) {
        return userRecordMapper.mapNonNull(loadUser(id));
    }

    @Override
    public UserRecord delete(UUID id) {
        try {
            UserEntity userEntity = loadUser(id);
            userEntity.setStatus(CommonStatus.UNACTIVE);
            return userRecordMapper.mapNonNull(userRepository.save(userEntity));
        } catch (PersistenceException pe) {
            throw new GenericException(pe);
        }
    }

    private UserEntity loadUser(UUID id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new NotFoundException("User with this id not found!");
    }
}
