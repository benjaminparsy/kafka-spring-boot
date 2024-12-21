package com.benjamin.parsy.ksb.review;

import com.benjamin.parsy.ksb.AbstractTest;
import com.benjamin.parsy.ksb.DataFaker;
import com.benjamin.parsy.ksb.book.Book;
import com.benjamin.parsy.ksb.book.BookRepository;
import com.benjamin.parsy.ksb.review.dto.ReviewRequestDto;
import com.benjamin.parsy.ksb.shared.service.JsonHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void createReviews_ReviewOk_ReturnReview() throws Exception {

        // Given
        Book book = bookRepository.findAll().getFirst();
        Review review = DataFaker.review(book);

        ReviewRequestDto reviewRequestDto = ReviewRequestDto.builder()
                .text(review.getText())
                .createdBy(review.getCreatedBy())
                .bookId(book.getId())
                .build();

        String json = JsonHelper.toJson(reviewRequestDto);

        // When
        String jsonResult = mockMvc.perform(post("/reviews")
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
        Review returnedReview = JsonHelper.toEntity(jsonResult, Review.class);

        assertNotNull(returnedReview.getId());
        assertEquals(review.getText(), returnedReview.getText());
        assertEquals(review.getCreatedBy(), returnedReview.getCreatedBy());
        assertNotNull(returnedReview.getBook().getId());
        assertNotNull(returnedReview.getBook().getTitle());
        assertNotNull(returnedReview.getBook().getCategory());
        assertNotNull(returnedReview.getBook().getCreatedDate());
        assertNotNull(returnedReview.getBook().getAuthor().getId());
        assertNotNull(returnedReview.getBook().getAuthor().getFirstname());
        assertNotNull(returnedReview.getBook().getAuthor().getLastname());

    }

}