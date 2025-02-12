package com.benjamin.parsy.ksb.user.entity.gateway;

import com.benjamin.parsy.ksb.user.entity.exception.UserNotFoundException;

import java.util.UUID;

public interface UserGateway {

    /**
     * Checks the existence of a user by its identifier
     *
     * @param uuid User identifier
     * @throws UserNotFoundException Throw if user is not found
     */
    void existsById(UUID uuid) throws UserNotFoundException;

}
