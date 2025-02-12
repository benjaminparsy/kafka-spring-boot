package com.benjamin.parsy.ksb.user.infrastructure.db.gateway;

import com.benjamin.parsy.ksb.user.entity.exception.UserNotFoundException;
import com.benjamin.parsy.ksb.user.entity.gateway.UserGateway;
import com.benjamin.parsy.ksb.user.entity.model.User;
import com.benjamin.parsy.ksb.user.infrastructure.db.jpa.repository.UserRepository;
import com.benjamin.parsy.ksb.user.infrastructure.db.jpa.schema.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JpaUserGateway implements UserGateway {

    private final UserRepository userRepository;

    @Override
    public User findById(UUID uuid) throws UserNotFoundException {

        Optional<UserEntity> optionalUserEntity = userRepository.findByUuid(uuid);

        if (optionalUserEntity.isEmpty()) {
            throw new UserNotFoundException("User not found for id " + uuid);
        }

        return optionalUserEntity.get().toUser();
    }

}
