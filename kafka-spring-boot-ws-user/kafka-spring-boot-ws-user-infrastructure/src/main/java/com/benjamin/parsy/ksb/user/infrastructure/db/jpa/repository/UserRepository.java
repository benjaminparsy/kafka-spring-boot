package com.benjamin.parsy.ksb.user.infrastructure.db.jpa.repository;

import com.benjamin.parsy.ksb.user.infrastructure.db.jpa.schema.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUuid(UUID uuid);

}
