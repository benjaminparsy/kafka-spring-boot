package com.benjamin.parsy.runnetic.user.infrastructure.db.jpa.port;

import com.benjamin.parsy.runnetic.user.usecase.exception.UserNotFoundException;
import com.benjamin.parsy.runnetic.user.usecase.port.UserPort;
import com.benjamin.parsy.runnetic.user.infrastructure.db.jpa.repository.UserRepository;
import com.benjamin.parsy.runnetic.user.infrastructure.db.jpa.schema.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JpaUserPort implements UserPort {

    private final UserRepository userRepository;

    @Override
    public void existsById(UUID uuid) throws UserNotFoundException {

        Optional<UserEntity> optionalUserEntity = userRepository.findByUuid(uuid);

        if (optionalUserEntity.isEmpty()) {
            throw new UserNotFoundException("User not found for id " + uuid);
        }

    }

}
