package com.benjamin.parsy.ksb.order.userprojection;

import com.benjamin.parsy.ksb.shared.service.MessageService;
import com.benjamin.parsy.ksb.shared.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserProjectionServiceImpl extends GenericServiceImpl<UserProjection> implements UserProjectionService {

    public UserProjectionServiceImpl(UserProjectionRepository repository, MessageService messageService) {
        super(repository, messageService);
    }

}
