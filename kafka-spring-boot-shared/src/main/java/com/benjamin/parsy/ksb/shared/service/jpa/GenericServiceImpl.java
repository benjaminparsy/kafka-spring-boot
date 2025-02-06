package com.benjamin.parsy.ksb.shared.service.jpa;

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
    public Optional<I> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public boolean deleteById(long id) {

        if (repository.findById(id).isEmpty()) {
            return false;
        }

        repository.deleteById(id);
        return repository.findById(id).isEmpty();
    }

}
