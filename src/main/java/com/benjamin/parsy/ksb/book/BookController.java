package com.benjamin.parsy.ksb.book;

import com.benjamin.parsy.ksb.author.Author;
import com.benjamin.parsy.ksb.author.AuthorService;
import com.benjamin.parsy.ksb.book.dto.BookRequestDto;
import com.benjamin.parsy.ksb.book.dto.BookResponseDto;
import com.benjamin.parsy.ksb.shared.exception.ErrorCode;
import com.benjamin.parsy.ksb.shared.exception.GlobalException;
import com.benjamin.parsy.ksb.shared.exception.RestException;
import com.benjamin.parsy.ksb.shared.service.MessageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@Slf4j
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final MessageService messageService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, AuthorService authorService,
                          MessageService messageService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.messageService = messageService;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getBooks(Integer limit, Integer offset) {
        List<Book> bookList = new LinkedList<>(bookService.findAllWithLimitAndOffset(limit, offset));
        return ResponseEntity.ok(bookMapper.toDtoList(bookList));
    }

    @PostMapping
    public ResponseEntity<BookResponseDto> createBook(@Valid @RequestBody BookRequestDto bookRequestDto) {

        Optional<Author> optionalAuthor = authorService.findById(bookRequestDto.getAuthorId());

        if (optionalAuthor.isEmpty()) {
            throw new RestException(messageService.getErrorMessage(ErrorCode.BR1, bookRequestDto.getAuthorId()));
        }

        Book bookCreated = bookService.save(Book.builder()
                .title(bookRequestDto.getTitle())
                .category(bookRequestDto.getCategory())
                .createdDate(LocalDateTime.now())
                .author(optionalAuthor.get())
                .build());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookMapper.toDto(bookCreated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookResponseDto> deleteBook(@PathVariable("id") @Min(0) Long bookId) {

        Optional<Book> optionalBook = bookService.findById(bookId);

        if (optionalBook.isEmpty()) {
            throw new RestException(messageService.getErrorMessage(ErrorCode.BR3, bookId));
        }

        try {
            Book bookDeleted = bookService.deleteById(bookId);
            return ResponseEntity.ok(bookMapper.toDto(bookDeleted));
        } catch (GlobalException e) {
            throw new RestException(e.getErrorMessage());
        }

    }

}
