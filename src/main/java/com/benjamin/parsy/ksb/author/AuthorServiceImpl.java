package com.benjamin.parsy.ksb.author;

import com.benjamin.parsy.ksb.shared.service.MessageService;
import com.benjamin.parsy.ksb.shared.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl extends GenericServiceImpl<Author> implements AuthorService {

    public AuthorServiceImpl(AuthorRepository repository, MessageService messageService) {
        super(repository, messageService);
    }

}
