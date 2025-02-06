package com.benjamin.parsy.ksb.shared.service.jpa;

import java.util.List;
import java.util.Optional;

public interface GenericService<I> {

    I save(I obj);

    List<I> findAll();

    Optional<I> findById(long id);

    boolean deleteById(long id);

}
