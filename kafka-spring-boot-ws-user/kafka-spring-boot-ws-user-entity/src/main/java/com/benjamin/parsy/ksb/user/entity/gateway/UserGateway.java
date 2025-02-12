package com.benjamin.parsy.ksb.user.entity.gateway;

import com.benjamin.parsy.ksb.user.entity.exception.UserNotFoundException;
import com.benjamin.parsy.ksb.user.entity.model.User;

import java.util.UUID;

public interface UserGateway {

    /**
     * To search for an user by its identifier
     *
     * @param uuid User identifier
     * @return The user optionally found
     * @throws UserNotFoundException Throw when user is not found
     */
    User findById(UUID uuid) throws UserNotFoundException;

}
