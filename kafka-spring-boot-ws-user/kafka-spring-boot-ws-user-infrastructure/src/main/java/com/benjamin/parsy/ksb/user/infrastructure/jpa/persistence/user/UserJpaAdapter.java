package com.benjamin.parsy.ksb.user.infrastructure.jpa.persistence.user;

import com.benjamin.parsy.ksb.user.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserJpaAdapter {

    public User toDomainModel(UserEntity userEntity) {
        return new User(
                userEntity.getUuid(),
                userEntity.getFirstname(),
                userEntity.getLastname(),
                userEntity.getEmail(),
                userEntity.getAddress(),
                userEntity.getPhone()
        );
    }

}
