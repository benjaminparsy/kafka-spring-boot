package com.benjamin.parsy.ksb.author;

import com.benjamin.parsy.ksb.author.dto.AuthorResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorResponseDto toDto(Author author);

    List<AuthorResponseDto> toDtoList(List<Author> authorList);

}
