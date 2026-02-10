package com.example.bloggingplatform.service.prod;

import com.example.bloggingplatform.dto.requestDto.TagRequestDto;
import com.example.bloggingplatform.dto.responseDto.TagResponseDto;
import com.example.bloggingplatform.entity.Tag;
import com.example.bloggingplatform.mapper.TagMapper;
import com.example.bloggingplatform.repository.TagRepository;
import com.example.bloggingplatform.service.noimlp.TagService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceProd implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagServiceProd(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional
    public TagResponseDto createTag(TagRequestDto tagRequestDto) {
        Tag newTag = tagRepository.save(tagMapper.toEntity(tagRequestDto));
        return tagMapper.toDto(newTag);
    }

    @Override
    @Transactional
    public TagResponseDto updateTagById(TagRequestDto tagRequestDto, Long id) {
        Tag oldTag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id" + id));

        oldTag.setName(tagRequestDto.getName());

        Tag updatedTag = tagRepository.save(oldTag);

        return tagMapper.toDto(updatedTag);
    }

    @Override
    @Transactional(readOnly = true)
    public TagResponseDto getTagById(Long id) {
        Tag foundTag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id" + id));

        return tagMapper.toDto(foundTag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponseDto> getTags() {
        List<TagResponseDto> foundTags = tagRepository.findAll().stream()
                .map(tagMapper::toDto)
                .toList();

        return foundTags;
    }

    @Override
    @Transactional
    public void deleteTagById(Long id) {
        tagRepository.deleteById(id);
    }
}
