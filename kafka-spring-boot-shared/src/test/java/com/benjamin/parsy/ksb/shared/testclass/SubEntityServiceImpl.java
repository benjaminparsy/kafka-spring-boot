package com.benjamin.parsy.ksb.shared.testclass;

import com.benjamin.parsy.ksb.shared.service.MessageService;
import com.benjamin.parsy.ksb.shared.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SubEntityServiceImpl extends GenericServiceImpl<SubEntity> implements SubEntityService {

    public SubEntityServiceImpl(SubEntityRepository repository, MessageService messageService) {
        super(repository, messageService);
    }

}
