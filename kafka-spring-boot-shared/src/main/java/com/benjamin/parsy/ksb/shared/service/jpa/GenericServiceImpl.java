package com.benjamin.parsy.ksb.shared.service.jpa;

import com.benjamin.parsy.ksb.shared.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class GenericServiceImpl<I> implements GenericService<I> {

    private final JpaRepository<I, Long> repository;

    protected GenericServiceImpl(JpaRepository<I, Long> repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public I save(I obj) {
        return repository.save(obj);
    }

    @Transactional(readOnly = true)
    @Override
    public List<I> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<I> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public I deleteById(long id) throws ResourceNotFoundException {

        I item = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));

        repository.deleteById(id);

        return item;
    }

}
