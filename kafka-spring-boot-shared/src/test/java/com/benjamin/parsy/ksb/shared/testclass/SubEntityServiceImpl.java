package com.benjamin.parsy.ksb.shared.testclass;

import com.benjamin.parsy.ksb.shared.service.jpa.GenericServiceImpl;
import com.benjamin.parsy.ksb.shared.service.message.MessageService;
import org.springframework.stereotype.Service;

@Service
public class SubEntityServiceImpl extends GenericServiceImpl<SubEntity> implements SubEntityService {

    public SubEntityServiceImpl(SubEntityRepository repository, MessageService messageService) {
        super(repository);
    }

}
