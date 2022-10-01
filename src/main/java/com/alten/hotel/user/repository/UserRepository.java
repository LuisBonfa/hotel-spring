package com.alten.hotel.user.repository;

import com.alten.hotel.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByName(@Param("name") String name);

    Optional<UserEntity> findByEmail(@Param("email") String email);
}
