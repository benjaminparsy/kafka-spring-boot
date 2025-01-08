package com.benjamin.parsy.ksb.book;

import com.benjamin.parsy.ksb.AbstractTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class BookRepositoryTest extends AbstractTest {

    @Autowired
    private BookRepository bookRepository;

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20})
    void findAllByOrderByCreatedDateLimitAndOffset_limitResult_shouldBeLessOrEgal(int limit) {

        // Given
        int offset = 0;

        // When
        List<Book> bookList = bookRepository.findAllByOrderByCreatedDateLimitAndOffset(limit, offset);

        // Then
        assertTrue(bookList.size() <= limit);

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void findAllByOrderByCreatedDateLimitAndOffset_OffsetResult_shouldSkipFirst() {

        // Given
        int limit = 20;
        int offset = 1;

        // When
        List<Book> bookList = bookRepository.findAllByOrderByCreatedDateLimitAndOffset(limit, offset);

        // Then
        assertEquals(3, bookList.size());

        Book book = bookList.stream()
                .findFirst()
                .orElseThrow();

        assertEquals("La Chute", book.getTitle());

    }

}
