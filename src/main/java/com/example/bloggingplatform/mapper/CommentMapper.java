package com.example.bloggingplatform.mapper;

import com.example.bloggingplatform.dto.requestDto.CommentRequestDto;
import com.example.bloggingplatform.dto.responseDto.CommentResponseDto;
import com.example.bloggingplatform.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    
    // Request -> Entity (Reverse Mapping)
    // @AfterMapping лучше использовать если есть связь Parent<->Child, глубокая вложенность,
    // конвертация типов, в нашем случае неоправданно и лучше для наглядности делегировать в сервис
    Comment toEntity(CommentRequestDto commentRequestDto);

    // Entity -> Response (Straight Mapping)
    @Mapping(source = "post.id", target = "postId")
    CommentResponseDto toDto(Comment comment);
}
