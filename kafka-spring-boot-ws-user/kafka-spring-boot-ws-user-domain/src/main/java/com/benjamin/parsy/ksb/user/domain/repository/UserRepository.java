package com.benjamin.parsy.ksb.user.domain.repository;

import com.benjamin.parsy.ksb.user.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    /**
     * To search for an user by its identifier
     *
     * @param uuid User identifier
     * @return The user optionally found
     */
    Optional<User> findById(UUID uuid);

}
