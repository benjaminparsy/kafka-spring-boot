package com.benjamin.parsy.ksb.user.infrastructure.jpa;

import com.benjamin.parsy.ksb.user.domain.model.User;
import com.benjamin.parsy.ksb.user.domain.repository.UserRepository;
import com.benjamin.parsy.ksb.user.infrastructure.jpa.persistence.user.UserJpaAdapter;
import com.benjamin.parsy.ksb.user.infrastructure.jpa.persistence.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRepositoryJpaImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserJpaAdapter userJpaAdapter;

    @Override
    public Optional<User> findById(UUID uuid) {

        return userJpaRepository.findByUuid(uuid)
                .map(userJpaAdapter::toDomainModel);

    }

}
