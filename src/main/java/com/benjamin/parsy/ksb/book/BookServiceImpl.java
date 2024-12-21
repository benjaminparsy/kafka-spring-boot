package com.benjamin.parsy.ksb.book;

import com.benjamin.parsy.ksb.shared.service.MessageService;
import com.benjamin.parsy.ksb.shared.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl extends GenericServiceImpl<Book> implements BookService {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository, MessageService messageService) {
        super(repository, messageService);
        this.repository = repository;
    }

    @Override
    public List<Book> findAllWithLimitAndOffset(Integer limit, Integer offset) {

        offset = offset == null ? 0 : offset;

        return limit == null ? repository.findAllByOrderByCreatedDateOffset(offset) :
                repository.findAllByOrderByCreatedDateLimitAndOffset(limit, offset);
    }

}
