package com.example.bloggingplatform.service.noimlp;

import com.example.bloggingplatform.dto.requestDto.TagRequestDto;
import com.example.bloggingplatform.dto.responseDto.TagResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    TagResponseDto createTag(TagRequestDto tagRequestDto);

    TagResponseDto updateTagById(TagRequestDto tagRequestDto, Long id);

    TagResponseDto getTagById(Long id);

    List<TagResponseDto> getTags();

    void deleteTagById(Long id);
}
