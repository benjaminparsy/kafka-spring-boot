package com.benjamin.parsy.ksb.review;

import com.benjamin.parsy.ksb.book.Book;
import com.benjamin.parsy.ksb.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "review")
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(targetEntity = Book.class, fetch = FetchType.LAZY, optional = false)
    private Book book;

}
