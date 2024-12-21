package com.benjamin.parsy.ksb;

import com.benjamin.parsy.ksb.author.Author;
import com.benjamin.parsy.ksb.book.Book;
import com.benjamin.parsy.ksb.review.Review;
import com.benjamin.parsy.ksb.shared.BaseEntity;
import com.github.javafaker.Faker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public class DataFaker {

    private static final Faker FAKER = new Faker();

    private DataFaker() {
        // Private constructor for utility class
    }

    public static Author author(JpaRepository<Author, Long> repository) {
        return save(repository, author());
    }

    public static Author author() {

        return Author.builder()
                .firstname(FAKER.name().firstName())
                .lastname(FAKER.name().lastName())
                .build();
    }

    public static Book book(JpaRepository<Book, Long> repository, Author author) {
        checkId(author, "author");
        return save(repository, book(author));
    }

    public static Book book(Author author) {

        return Book.builder()
                .author(author)
                .title(FAKER.book().title())
                .category(FAKER.book().genre())
                .createdDate(LocalDateTime.now())
                .build();
    }

    public static Review review(JpaRepository<Review, Long> repository, Book book) {
        checkId(book, "book");
        return save(repository, review(book));
    }

    public static Review review(Book book) {

        return Review.builder()
                .book(book)
                .text("Some comment")
                .createdBy(FAKER.name().fullName())
                .build();
    }

    private static <S extends BaseEntity> S save(JpaRepository<S, Long> repository, S entity) {
        return repository.save(entity);
    }

    private static void checkId(BaseEntity baseEntity, String name) {

        if (baseEntity.getId() == null) {
            throw new IllegalArgumentException(String.format("Entity '%s' must have an id", name));
        }

    }

}
