package com.benjamin.parsy.ksb.shared.testclass;

import com.benjamin.parsy.ksb.shared.service.jpa.GenericServiceImpl;
import com.benjamin.parsy.ksb.shared.service.message.MessageService;
import org.springframework.stereotype.Service;

@Service
public class EntityServiceImpl extends GenericServiceImpl<Entity> implements EntityService {

    public EntityServiceImpl(EntityRepository repository, MessageService messageService) {
        super(repository);
    }

}
