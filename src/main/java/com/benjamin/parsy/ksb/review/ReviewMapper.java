package com.benjamin.parsy.ksb.review;

import com.benjamin.parsy.ksb.book.BookMapper;
import com.benjamin.parsy.ksb.review.dto.ReviewResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface ReviewMapper {

    @Mapping(target = "bookResponseDto", source = "book")
    ReviewResponseDto toDto(Review review);

}
