package com.example.bloggingplatform.service.prod;

import com.example.bloggingplatform.dto.requestDto.TagRequestDto;
import com.example.bloggingplatform.dto.responseDto.TagResponseDto;
import com.example.bloggingplatform.entity.Tag;
import com.example.bloggingplatform.error.NotFoundException;
import com.example.bloggingplatform.mapper.TagMapper;
import com.example.bloggingplatform.repository.TagRepository;
import com.example.bloggingplatform.service.noimlp.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
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
        log.info("Creating new tag with name: {}", tagRequestDto.getName());

        Tag newTag = tagRepository.save(tagMapper.toEntity(tagRequestDto));
        log.info("Tag created successfully with name: {}", tagRequestDto.getName());
        return tagMapper.toDto(newTag);
    }

    @Override
    @Transactional
    public TagResponseDto updateTagById(TagRequestDto tagRequestDto, Long id) {
        log.info("Updating tag with id: {}", id);

        Tag oldTag = tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tag not found with id" + id));

        oldTag.setName(tagRequestDto.getName());

        Tag updatedTag = tagRepository.save(oldTag);

        log.info("Tag updated successfully with id: {}", id);
        return tagMapper.toDto(updatedTag);
    }

    @Override
    @Transactional(readOnly = true)
    public TagResponseDto getTagById(Long id) {
        Tag foundTag = tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tag not found with id" + id));

        return tagMapper.toDto(foundTag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponseDto> getTags() {
        List<TagResponseDto> foundTags = tagRepository.findAll().stream()
                .map(tagMapper::toDto)
                .sorted(Comparator.comparing(TagResponseDto::getId))
                .toList();

        return foundTags;
    }

    @Override
    @Transactional
    public void deleteTagById(Long id) {
        log.info("Deleting tag with id: {}", id);
        tagRepository.deleteById(id);
        log.info("Tag deleted successfully with id: {}", id);
    }
}
