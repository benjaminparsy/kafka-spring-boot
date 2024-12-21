package com.benjamin.parsy.ksb.book;

import com.benjamin.parsy.ksb.author.AuthorMapper;
import com.benjamin.parsy.ksb.book.dto.BookResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public interface BookMapper {

    @Mapping(target = "authorResponseDto", source = "author")
    BookResponseDto toDto(Book book);

    List<BookResponseDto> toDtoList(List<Book> bookList);

}
