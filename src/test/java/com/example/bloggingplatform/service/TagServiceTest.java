package com.example.bloggingplatform.service;

import com.example.bloggingplatform.dto.requestDto.TagRequestDto;
import com.example.bloggingplatform.dto.responseDto.TagResponseDto;
import com.example.bloggingplatform.entity.Tag;
import com.example.bloggingplatform.mapper.TagMapper;
import com.example.bloggingplatform.repository.TagRepository;
import com.example.bloggingplatform.service.prod.TagServiceProd;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @InjectMocks
    private TagServiceProd tagServiceProd;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagMapper tagMapper;

    private Tag tag;
    private Tag tag2;

    private TagRequestDto tagRequestDto;
    private TagRequestDto tagRequestDto2;

    private TagResponseDto tagResponseDto;
    private TagResponseDto tagResponseDto2;

    private Long tagId = 1L;
    private Long tagId2 = 2L;

    private String name = "Cat";
    private String name2 = "Dog";

    @BeforeEach
    public void init(){
        tag = Tag.builder().id(tagId).name(name).build();
        tag2 = Tag.builder().id(tagId2).name(name2).build();

        tagResponseDto = TagResponseDto.builder().id(tagId).name(name).build();
        tagResponseDto2 = TagResponseDto.builder().id(tagId2).name(name2).build();

        tagRequestDto = TagRequestDto.builder().name(name).build();
        tagRequestDto2 = TagRequestDto.builder().name(name2).build();
    }

    @Test
    public void shouldCreateGroup() throws Exception{
        when(tagMapper.toEntity(tagRequestDto)).thenReturn(tag);
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);
        when(tagMapper.toDto(tag)).thenReturn(tagResponseDto);

        TagResponseDto savedTag = tagServiceProd.createTag(tagRequestDto);

        assertThat(savedTag).isNotNull();
        assertThat(savedTag.getName()).isEqualTo(name);
    }
    @Test
    public void shouldGetGroupById() throws Exception{
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));
        when(tagMapper.toDto(any(Tag.class))).thenReturn(tagResponseDto);

        TagResponseDto savedTag = tagServiceProd.getTagById(tagId);

        assertThat(savedTag).isNotNull();
        assertThat(savedTag.getName()).isEqualTo(name);
    }
    @Test
    public void shouldGetAllGroups() throws Exception{
        List<Tag> tags = Arrays.asList(tag, tag2);

        when(tagRepository.findAll()).thenReturn(tags);
        when(tagMapper.toDto(any(Tag.class))).thenAnswer(invocation -> {
            Tag t = invocation.getArgument(0);
            return switch ((int) (long) t.getId()) {
                case 1 -> tagResponseDto;
                case 2 -> tagResponseDto2;
                default -> throw new RuntimeException("Unknown tag id: " + t.getId());
            };
        });

        List<TagResponseDto> result = tagServiceProd.getTags();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(tagResponseDto, tagResponseDto2);
    }
    @Test
    public void shouldDeleteGroup() throws Exception{
        tagServiceProd.deleteTagById(tagId);

        verify(tagRepository).deleteById(tagId);
    }
    @Test
    public void shouldUpdateGroup() throws Exception{
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);
        when(tagMapper.toDto(tag)).thenReturn(tagResponseDto);

        TagResponseDto savedTag = tagServiceProd.updateTagById(tagRequestDto, tagId);

        assertThat(savedTag).isNotNull();
    }
}
