package com.alten.hotel.role.repository;

import com.alten.hotel.role.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    
    Set<RoleEntity> findByNameIn(@Param("names") List<String> names);
}
