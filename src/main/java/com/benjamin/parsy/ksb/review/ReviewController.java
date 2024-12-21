package com.benjamin.parsy.ksb.review;

import com.benjamin.parsy.ksb.book.Book;
import com.benjamin.parsy.ksb.book.BookService;
import com.benjamin.parsy.ksb.review.dto.ReviewRequestDto;
import com.benjamin.parsy.ksb.review.dto.ReviewResponseDto;
import com.benjamin.parsy.ksb.shared.exception.ErrorCode;
import com.benjamin.parsy.ksb.shared.exception.RestException;
import com.benjamin.parsy.ksb.shared.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final BookService bookService;
    private final MessageService messageService;
    private final ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService, BookService bookService,
                            MessageService messageService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.bookService = bookService;
        this.messageService = messageService;
        this.reviewMapper = reviewMapper;
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@Valid @RequestBody ReviewRequestDto reviewRequestDto) {

        Optional<Book> optionalBook = bookService.findById(reviewRequestDto.getBookId());

        if (optionalBook.isEmpty()) {
            throw new RestException(messageService.getErrorMessage(ErrorCode.BR3, reviewRequestDto.getBookId()));
        }

        Review reviewCreated = reviewService.save(Review.builder()
                .text(reviewRequestDto.getText())
                .createdBy(reviewRequestDto.getCreatedBy())
                .book(optionalBook.get())
                .build());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewMapper.toDto(reviewCreated));
    }

}