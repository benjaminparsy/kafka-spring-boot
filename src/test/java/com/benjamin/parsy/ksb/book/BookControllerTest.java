package com.benjamin.parsy.ksb.book;

import com.benjamin.parsy.ksb.DataFaker;
import com.benjamin.parsy.ksb.author.Author;
import com.benjamin.parsy.ksb.author.AuthorRepository;
import com.benjamin.parsy.ksb.book.dto.BookRequestDto;
import com.benjamin.parsy.ksb.handler.ErrorResponse;
import com.benjamin.parsy.ksb.shared.service.JsonHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void getBooks_BooksPresent_ReturnAllBooks() throws Exception {

        // When
        String jsonResult = mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Then
        List<Book> bookList = JsonHelper.toEntityCollection(jsonResult, Book.class, new ArrayList<>());

        assertEquals(4, bookList.size());
        bookList.forEach(book -> {
            assertNotNull(book.getId());
            assertNotNull(book.getTitle());
            assertNotNull(book.getCategory());
            assertNotNull(book.getCreatedDate());
            assertNotNull(book.getAuthor());
        });

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void createBooks_BookOk_ReturnBook() throws Exception {

        // Given
        Author author = authorRepository.findAll().getFirst();
        Book book = DataFaker.book(author);

        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .title(book.getTitle())
                .category(book.getCategory())
                .authorId(author.getId())
                .build();

        String json = JsonHelper.toJson(bookRequestDto);

        // When
        String jsonResult = mockMvc.perform(post("/books")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Then
        Book returnedBook = JsonHelper.toEntity(jsonResult, Book.class);

        assertNotNull(returnedBook.getId());
        assertEquals(book.getTitle(), returnedBook.getTitle());
        assertEquals(book.getCategory(), returnedBook.getCategory());
        assertNotNull(returnedBook.getAuthor().getId());
        assertNotNull(returnedBook.getAuthor().getFirstname());
        assertNotNull(returnedBook.getAuthor().getLastname());

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void createBooks_BodyIncorrect_ThrowException() throws Exception {

        // Given
        Author author = authorRepository.findAll().getFirst();
        Book book = DataFaker.book(author);

        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .category(book.getCategory())
                .authorId(author.getId())
                .build();

        String json = JsonHelper.toJson(bookRequestDto);

        // When
        String jsonResult = mockMvc.perform(post("/books")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Then
        ErrorResponse errorResponse = JsonHelper.toEntity(jsonResult, ErrorResponse.class);

        assertEquals("[BR8]", errorResponse.getCode());
        assertEquals("An error has occurred during input data validation, see violations", errorResponse.getDescription());
        assertNotNull(errorResponse.getExtensions());
        assertEquals(1, errorResponse.getExtensions().size());

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void createBooks_AuthorNotFound_ThrowException() throws Exception {

        // Given
        Book book = DataFaker.book(DataFaker.author());
        Long authorIdUnknown = authorRepository.findAll().getLast().getId() + 1;

        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .title(book.getTitle())
                .category(book.getCategory())
                .authorId(authorIdUnknown)
                .build();

        String json = JsonHelper.toJson(bookRequestDto);

        // When
        String jsonResult = mockMvc.perform(post("/books")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Then
        ErrorResponse errorResponse = JsonHelper.toEntity(jsonResult, ErrorResponse.class);

        assertEquals("[BR1]", errorResponse.getCode());
        assertEquals(String.format("Author with ID %s does not exist", authorIdUnknown), errorResponse.getDescription());
        assertNull(errorResponse.getExtensions());

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void deleteBooks_BookExist_DeleteOk() throws Exception {

        // Given
        Author author = DataFaker.author(authorRepository);
        Book book = DataFaker.book(bookRepository, author);

        // When and then
        String jsonResult = mockMvc.perform(delete("/books/{id}", book.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Then
        Book returnedBook = JsonHelper.toEntity(jsonResult, Book.class);

        assertNotNull(returnedBook.getId());
        assertEquals(book.getTitle(), returnedBook.getTitle());
        assertEquals(book.getCategory(), returnedBook.getCategory());
        assertNotNull(returnedBook.getAuthor().getId());
        assertNotNull(returnedBook.getAuthor().getFirstname());
        assertNotNull(returnedBook.getAuthor().getLastname());

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void deleteBooks_BookNotExist_ThrowException() throws Exception {

        // Given
        Long bookIdUnknown = bookRepository.findAll().getLast().getId() + 1;

        // When and then
        String jsonResult = mockMvc.perform(delete("/books/{id}", bookIdUnknown))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Then
        ErrorResponse errorResponse = JsonHelper.toEntity(jsonResult, ErrorResponse.class);

        assertEquals("[BR3]", errorResponse.getCode());
        assertEquals(String.format("Book with ID %s does not exist", bookIdUnknown), errorResponse.getDescription());
        assertNull(errorResponse.getExtensions());

    }

}