package com.benjamin.parsy.ksb.review.dto;

import com.benjamin.parsy.ksb.book.dto.BookResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDto {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String text;

    @JsonProperty
    private String createdBy;

    @JsonProperty("book")
    private BookResponseDto bookResponseDto;

}
