package com.benjamin.parsy.runnetic.user.usecase.port;

import com.benjamin.parsy.runnetic.user.usecase.exception.UserNotFoundException;

import java.util.UUID;

public interface UserPort {

    /**
     * Checks the existence of a user by its identifier
     *
     * @param uuid User identifier
     * @throws UserNotFoundException Throw if user is not found
     */
    void existsById(UUID uuid) throws UserNotFoundException;

}
