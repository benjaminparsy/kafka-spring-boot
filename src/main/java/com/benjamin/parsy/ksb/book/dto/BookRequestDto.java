package com.benjamin.parsy.ksb.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequestDto {

    @NotEmpty
    private String title;

    private String category;

    @NotNull
    @Min(0)
    private Long authorId;

}
