package com.benjamin.parsy.ksb.order.userprojection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProjectionRepository extends JpaRepository<UserProjection, Long> {

    Optional<UserProjection> findByEmail(String email);

}
