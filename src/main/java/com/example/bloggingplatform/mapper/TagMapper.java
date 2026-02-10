package com.example.bloggingplatform.mapper;

import com.example.bloggingplatform.dto.requestDto.TagRequestDto;
import com.example.bloggingplatform.dto.responseDto.TagResponseDto;
import com.example.bloggingplatform.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    //Straight Mapping
    Tag toEntity(TagRequestDto tagRequestDto);

    //Reverse Mapping
    TagResponseDto toDto(Tag tag);
}
