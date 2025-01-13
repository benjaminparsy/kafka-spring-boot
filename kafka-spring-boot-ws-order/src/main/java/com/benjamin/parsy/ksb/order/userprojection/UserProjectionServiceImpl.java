package com.benjamin.parsy.ksb.order.userprojection;

import com.benjamin.parsy.ksb.shared.service.jpa.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserProjectionServiceImpl extends GenericServiceImpl<UserProjection> implements UserProjectionService {

    public UserProjectionServiceImpl(UserProjectionRepository repository) {
        super(repository);
    }

}
