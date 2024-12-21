package com.benjamin.parsy.ksb.book;

import com.benjamin.parsy.ksb.shared.service.GenericService;

import java.util.List;

public interface BookService extends GenericService<Book> {

    List<Book> findAllWithLimitAndOffset(Integer limit, Integer offset);

}
