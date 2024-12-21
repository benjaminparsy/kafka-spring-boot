package com.benjamin.parsy.ksb.shared.service;

import com.benjamin.parsy.ksb.AbstractTest;
import com.benjamin.parsy.ksb.DataFaker;
import com.benjamin.parsy.ksb.author.Author;
import com.benjamin.parsy.ksb.book.Book;
import com.benjamin.parsy.ksb.shared.exception.GlobalException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GenericServiceTest extends AbstractTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GenericService<Book> genericService;

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void save_EntityOk_ReturnEntity() {

        // Given
        Author author = jdbcTemplate.queryForObject("SELECT * FROM author LIMIT 1",
                (rs, rowNum) -> {
                    Author authorJdbc = Author.builder()
                            .firstname(rs.getString("firstname"))
                            .lastname(rs.getString("lastname"))
                            .build();
                    authorJdbc.setId(rs.getLong("id"));
                    return authorJdbc;
                });
        Book book = DataFaker.book(author);

        // When
        book = genericService.save(book);

        // Then
        assertNotNull(book.getId());

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void findAll_EntitiessPresent_ReturnEntityList() {

        // When
        List<Book> bookList = genericService.findAll();

        // Then
        assertEquals(4, bookList.size());

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void findById_EntityPresent_ReturnEntity() {

        // Given
        Long id = jdbcTemplate.queryForObject("SELECT id FROM book LIMIT 1", Long.class);

        // When
        Optional<Book> optionalBook = genericService.findById(id);

        // Then
        assertTrue(optionalBook.isPresent());

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void deleteById_deleteOk() {

        // Given
        Long id = jdbcTemplate.queryForObject("SELECT id FROM book LIMIT 1", Long.class);

        // When and then
        assertDoesNotThrow(() -> genericService.deleteById(id));

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void deleteById_idNotExist_throwException() {

        // Given
        long lastId = jdbcTemplate.queryForObject("SELECT id FROM book ORDER BY id DESC LIMIT 1;", Long.class);
        long idUnknown = lastId + 1;

        // When
        GlobalException exception = assertThrows(GlobalException.class,
                () -> genericService.deleteById(idUnknown));

        // Then
        String msg = String.format("[IE1] Item with id %s cannot be found in database", idUnknown);
        assertEquals(msg, exception.getMessage());

    }

}