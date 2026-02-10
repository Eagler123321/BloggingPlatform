package com.example.bloggingplatform.mapper;

import com.example.bloggingplatform.dto.requestDto.PostRequestDto;
import com.example.bloggingplatform.dto.responseDto.PostResponseDto;
import com.example.bloggingplatform.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    // Straight Mapping
    PostResponseDto toDto(Post post);
    // Reverse Mapping
    Post toEntity(PostRequestDto postRequestDto);
}
