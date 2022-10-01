package com.alten.hotel.role.service;

import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.common.exception.exceptions.GenericException;
import com.alten.hotel.common.exception.exceptions.NotFoundException;
import com.alten.hotel.common.mapper.Mapper;
import com.alten.hotel.common.service.DefaultService;
import com.alten.hotel.role.dto.RoleDTO;
import com.alten.hotel.role.dto.RoleRecord;
import com.alten.hotel.role.entity.RoleEntity;
import com.alten.hotel.role.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleService implements DefaultService<RoleDTO, RoleRecord> {

    RoleRepository roleRepository;
    Mapper<RoleEntity, RoleRecord> roleRecordMapper;
    Mapper<RoleDTO, RoleEntity> roleEntityMapper;

    @Override
    public RoleRecord delete(UUID id) {
        RoleEntity roleEntity = loadRole(id);
        roleEntity.setStatus(CommonStatus.UNACTIVE);
        return roleRecordMapper.mapNonNull(roleRepository.save(roleEntity));
    }

    @Override
    public List<RoleRecord> findAll() {
        return roleRepository.findAll().stream().map(roleRecordMapper::mapNonNull).collect(Collectors.toList());
    }

    @Override
    public RoleRecord findById(UUID id) {
        return roleRecordMapper.mapNonNull(loadRole(id));
    }

    @Override
    public RoleRecord save(RoleDTO data) {
        try {
            RoleEntity entity = roleEntityMapper.mapNonNull(data);
            return roleRecordMapper.mapNonNull(roleRepository.save(entity));
        } catch (PersistenceException ex) {
            throw new GenericException(ex);
        }
    }

    @Override
    public RoleRecord update(UUID id, RoleDTO data) {
        try {
            RoleEntity role = loadRole(id);
            role.setName(data.getName());
            return roleRecordMapper.mapNonNull(roleRepository.save(role));
        } catch (PersistenceException ex) {
            throw new GenericException(ex);
        }
    }

    private RoleEntity loadRole(UUID id) {
        Optional<RoleEntity> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        }
        throw new NotFoundException("Couldn't find role with this id");
    }

    public Set<RoleEntity> findByNameIn(List<String> roles) {
        return roleRepository.findByNameIn(roles);
    }
}
