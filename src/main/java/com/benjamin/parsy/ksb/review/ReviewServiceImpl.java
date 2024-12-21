package com.benjamin.parsy.ksb.review;

import com.benjamin.parsy.ksb.shared.service.MessageService;
import com.benjamin.parsy.ksb.shared.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl extends GenericServiceImpl<Review> implements ReviewService {

    private final ReviewRepository repository;

    protected ReviewServiceImpl(ReviewRepository repository, MessageService messageService) {
        super(repository, messageService);
        this.repository = repository;
    }

}
