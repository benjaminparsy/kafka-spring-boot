package com.benjamin.parsy.ksb.review.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDto {

    @NotEmpty
    private String text;

    @NotEmpty
    private String createdBy;

    @NotNull
    @Min(0)
    private Long bookId;

}
