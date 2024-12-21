package com.benjamin.parsy.ksb.book.dto;

import com.benjamin.parsy.ksb.author.dto.AuthorResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponseDto {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String category;

    @JsonProperty
    private LocalDateTime createdDate;

    @JsonProperty("author")
    private AuthorResponseDto authorResponseDto;

}
