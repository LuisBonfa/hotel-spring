package com.alten.hotel.role.service;

import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.common.exception.exceptions.GenericException;
import com.alten.hotel.common.exception.exceptions.NotFoundException;
import com.alten.hotel.common.mapper.Mapper;
import com.alten.hotel.role.dto.RoleDTO;
import com.alten.hotel.role.dto.RoleRecord;
import com.alten.hotel.role.entity.RoleEntity;
import com.alten.hotel.role.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.experimental.runners.Enclosed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(Enclosed.class)
@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class ExceptionsTest {

        @Mock
        RoleRepository roleRepository;

        @Mock
        Mapper<RoleEntity, RoleRecord> roleRecordMapper;

        @Mock
        Mapper<RoleDTO, RoleEntity> roleEntityMapper;

        @Spy
        @InjectMocks
        RoleService roleService;

        @Before
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void deleteWithRoleNotFoundExceptionTest() {
            Assertions.assertThrows(NotFoundException.class, () -> {
                roleService.delete(UUID.randomUUID());
            });
        }

        @Test
        public void saveWithPersistenceExceptionTest() {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setName("teste");

            RoleEntity role = new RoleEntity();
            role.setName("teste");

            when(roleEntityMapper.mapNonNull(roleDTO)).thenReturn(role);
            when(roleRepository.save(role)).thenThrow(PersistenceException.class);
            Assertions.assertThrows(GenericException.class, () -> {
                roleService.save(roleDTO);
            });
        }

        @Test
        public void updateWithNotFoundExceptionTest() {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setName("teste");

            Assertions.assertThrows(NotFoundException.class, () -> {
                roleService.update(UUID.randomUUID(), roleDTO);
            });
        }

        @Test
        public void updateWithPersistenceExceptionTest() {

            UUID roleId = UUID.randomUUID();

            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setName("teste");

            RoleEntity role = new RoleEntity();
            role.setId(roleId);
            role.setName("teste");

            when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
            when(roleRepository.save(role)).thenThrow(PersistenceException.class);
            Assertions.assertThrows(GenericException.class, () -> {
                roleService.update(roleId, roleDTO);
            });
        }
    }

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class WorkingTest {
        @Mock
        RoleRepository roleRepository;

        @Mock
        Mapper<RoleEntity, RoleRecord> roleRecordMapper;

        @Mock
        Mapper<RoleDTO, RoleEntity> roleEntityMapper;

        @Spy
        @InjectMocks
        RoleService roleService;

        @Before
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void deleteTest() {
            UUID roleId = UUID.randomUUID();

            RoleEntity role = new RoleEntity();
            role.setId(roleId);
            role.setName("teste");
            role.setStatus(CommonStatus.ACTIVE);

            RoleRecord roleRecord = RoleRecord.builder().name("teste").status(CommonStatus.UNACTIVE).build();

            when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
            when(roleRepository.save(role)).thenReturn(role);
            when(roleRecordMapper.mapNonNull(role)).thenReturn(roleRecord);

            RoleRecord record = roleService.delete(roleId);

            Assertions.assertEquals(role.getStatus(), CommonStatus.UNACTIVE);
            Assertions.assertEquals(role.getName(), record.getName());

            verify(roleRepository, times(1)).findById(roleId);
            verify(roleRepository, times(1)).save(role);
            verify(roleRecordMapper, times(1)).mapNonNull(role);

            InOrder order = Mockito.inOrder(roleRepository, roleRecordMapper);
            order.verify(roleRepository).findById(roleId);
            order.verify(roleRepository).save(role);
            order.verify(roleRecordMapper).mapNonNull(role);
            order.verifyNoMoreInteractions();
        }

        @Test
        public void saveTest() {
            UUID roleId = UUID.randomUUID();

            RoleEntity role = new RoleEntity();
            role.setId(roleId);
            role.setName("teste");
            role.setStatus(CommonStatus.ACTIVE);

            RoleDTO roleDTO = new RoleDTO();
            role.setName("teste");

            RoleRecord roleRecord = RoleRecord.builder().name("teste").status(CommonStatus.ACTIVE).build();

            when(roleEntityMapper.mapNonNull(roleDTO)).thenReturn(role);
            when(roleRepository.save(role)).thenReturn(role);
            when(roleRecordMapper.mapNonNull(role)).thenReturn(roleRecord);

            RoleRecord record = roleService.save(roleDTO);

            Assertions.assertEquals(role.getStatus(), CommonStatus.ACTIVE);
            Assertions.assertEquals(role.getName(), record.getName());

            verify(roleEntityMapper, times(1)).mapNonNull(roleDTO);
            verify(roleRepository, times(1)).save(role);
            verify(roleRecordMapper, times(1)).mapNonNull(role);

            InOrder order = Mockito.inOrder(roleEntityMapper, roleRepository, roleRecordMapper);
            order.verify(roleEntityMapper).mapNonNull(roleDTO);
            order.verify(roleRepository).save(role);
            order.verify(roleRecordMapper).mapNonNull(role);
            order.verifyNoMoreInteractions();
        }

        @Test
        public void updateTest() {
            UUID roleId = UUID.randomUUID();

            RoleEntity role = new RoleEntity();
            role.setId(roleId);
            role.setName("teste");
            role.setStatus(CommonStatus.ACTIVE);

            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setName("teste");

            RoleRecord roleRecord = RoleRecord.builder().name("teste").status(CommonStatus.ACTIVE).build();

            when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
            when(roleRepository.save(role)).thenReturn(role);
            when(roleRecordMapper.mapNonNull(role)).thenReturn(roleRecord);

            RoleRecord record = roleService.update(roleId, roleDTO);

            Assertions.assertEquals(role.getStatus(), CommonStatus.ACTIVE);
            Assertions.assertEquals(role.getName(), record.getName());

            verify(roleRepository, times(1)).findById(roleId);
            verify(roleRepository, times(1)).save(role);
            verify(roleRecordMapper, times(1)).mapNonNull(role);

            InOrder order = Mockito.inOrder(roleRepository, roleRecordMapper);
            order.verify(roleRepository).findById(roleId);
            order.verify(roleRepository).save(role);
            order.verify(roleRecordMapper).mapNonNull(role);
            order.verifyNoMoreInteractions();
        }

        @Test
        public void findByNameInTest() {
            RoleEntity role = new RoleEntity();
            role.setId(UUID.randomUUID());
            role.setName("teste");
            role.setStatus(CommonStatus.ACTIVE);

            List<String> roleNames = List.of("teste");

            when(roleRepository.findByNameIn(roleNames)).thenReturn(Set.of(role));

            Set<RoleEntity> roles = roleService.findByNameIn(List.of("teste"));

            RoleEntity resultRole = roles.iterator().next();
            Assertions.assertEquals(1, roles.size());
            Assertions.assertEquals(role.getId(), resultRole.getId());
            Assertions.assertEquals(role.getName(), resultRole.getName());
            Assertions.assertEquals(role.getStatus(), resultRole.getStatus());

            verify(roleRepository, times(1)).findByNameIn(roleNames);

            InOrder order = Mockito.inOrder(roleRepository);
            order.verify(roleRepository).findByNameIn(roleNames);
            order.verifyNoMoreInteractions();
        }
    }
}