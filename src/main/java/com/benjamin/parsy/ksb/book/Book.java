package com.benjamin.parsy.ksb.book;

import com.benjamin.parsy.ksb.author.Author;
import com.benjamin.parsy.ksb.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "book")
@Table(name = "book")
public class Book extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "category")
    private String category;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @ManyToOne(targetEntity = Author.class, fetch = FetchType.LAZY, optional = false)
    private Author author;

}
