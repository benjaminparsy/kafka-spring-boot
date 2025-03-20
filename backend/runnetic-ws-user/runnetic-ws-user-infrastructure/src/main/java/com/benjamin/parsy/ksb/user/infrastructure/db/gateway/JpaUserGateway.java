package com.benjamin.parsy.runnetic.user.infrastructure.db.gateway;

import com.benjamin.parsy.runnetic.user.entity.exception.UserNotFoundException;
import com.benjamin.parsy.runnetic.user.entity.gateway.UserGateway;
import com.benjamin.parsy.runnetic.user.infrastructure.db.jpa.repository.UserRepository;
import com.benjamin.parsy.runnetic.user.infrastructure.db.jpa.schema.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JpaUserGateway implements UserGateway {

    private final UserRepository userRepository;

    @Override
    public void existsById(UUID uuid) throws UserNotFoundException {

        Optional<UserEntity> optionalUserEntity = userRepository.findByUuid(uuid);

        if (optionalUserEntity.isEmpty()) {
            throw new UserNotFoundException("User not found for id " + uuid);
        }

    }

}
