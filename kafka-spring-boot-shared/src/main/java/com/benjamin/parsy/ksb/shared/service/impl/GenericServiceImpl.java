package com.benjamin.parsy.ksb.shared.service.impl;

import com.benjamin.parsy.ksb.shared.exception.ErrorCode;
import com.benjamin.parsy.ksb.shared.exception.GlobalException;
import com.benjamin.parsy.ksb.shared.service.GenericService;
import com.benjamin.parsy.ksb.shared.service.MessageService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class GenericServiceImpl<I> implements GenericService<I> {

    private final JpaRepository<I, Long> repository;
    private final MessageService messageService;

    protected GenericServiceImpl(JpaRepository<I, Long> repository, MessageService messageService) {
        this.repository = repository;
        this.messageService = messageService;
    }

    public I save(I obj) {
        return repository.save(obj);
    }

    public List<I> findAll() {
        return repository.findAll();
    }

    public Optional<I> findById(Long id) {
        return repository.findById(id);
    }

    public I deleteById(long id) throws GlobalException {

        I item = repository.findById(id).orElseThrow(() ->
                new GlobalException(messageService.getErrorMessage(ErrorCode.IE1, id)));

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new GlobalException(messageService.getErrorMessage(ErrorCode.IE2, id));
        }

        return item;
    }

}
