package com.benjamin.parsy.ksb.author;

import com.benjamin.parsy.ksb.author.dto.AuthorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDto>> getAuthors() {
        List<Author> authorList = new LinkedList<>(authorService.findAll());
        return ResponseEntity.ok(authorMapper.toDtoList(authorList));
    }

}
