package com.benjamin.parsy.ksb.shared.service.jpa;

import com.benjamin.parsy.ksb.shared.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface GenericService<I> {

    I save(I obj);

    List<I> findAll();

    Optional<I> findById(Long id);

    I deleteById(long id) throws ResourceNotFoundException;

}
