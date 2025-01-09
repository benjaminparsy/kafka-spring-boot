package com.benjamin.parsy.ksb.shared.testclass;

import com.benjamin.parsy.ksb.shared.service.MessageService;
import com.benjamin.parsy.ksb.shared.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EntityServiceImpl extends GenericServiceImpl<Entity> implements EntityService {

    public EntityServiceImpl(EntityRepository repository, MessageService messageService) {
        super(repository, messageService);
    }

}
