package com.example.bloggingplatform.controller;

import com.example.bloggingplatform.dto.requestDto.PostRequestDto;
import com.example.bloggingplatform.dto.responseDto.PostResponseDto;
import com.example.bloggingplatform.dto.responseDto.TagResponseDto;
import com.example.bloggingplatform.service.prod.PostServiceProd;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PostServiceProd postServiceProd;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private PostRequestDto postRequestDto;
    private PostResponseDto postResponseDto;

    private final String title = "animal";
    private final String content = "my animal";
    private final Long postId = 1L;
    private final LocalDateTime now = LocalDateTime.now();
    private final String firstTag = "Dog";
    private final String secondTag = "Cat";
    private final Set<TagResponseDto> tags = Set.of(
            new TagResponseDto(1L, firstTag),
            new TagResponseDto(2L, secondTag)
    );

    @BeforeEach
    public void init(){
        Set<Long> tagIds = Set.of(1L, 2L);

        postRequestDto = PostRequestDto.builder().title(title).content(content).tagIds(tagIds).build();

        postResponseDto = PostResponseDto.builder()
                .id(postId)
                .title(title)
                .content(content)
                .createdAt(now)
                .updatedAt(now)
                .tags(tags)
                .build();
    }

    @Test
    public void PostController_CreatePost_CreatedPost() throws Exception{
        given(postServiceProd.createPost(any(PostRequestDto.class)))
                .willReturn(postResponseDto);

        ResultActions response = mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequestDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", CoreMatchers.is(postRequestDto.getTitle())))
                .andExpect(jsonPath("$.content", CoreMatchers.is(postRequestDto.getContent())))
                .andExpect(jsonPath("$.tags[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$.tags[*].name", containsInAnyOrder("Dog", "Cat")));
    }

    @Test
    public void PostController_GetPostById_ReceivedPost() throws Exception{
        when(postServiceProd.getPostById(postId)).thenReturn(postResponseDto);

        ResultActions response = mockMvc.perform(get("/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.title", CoreMatchers.is(postResponseDto.getTitle())))
                .andExpect(jsonPath("$.content", CoreMatchers.is(postResponseDto.getContent())))
                .andExpect(jsonPath("$.tags[*].name", containsInAnyOrder("Dog", "Cat")));
    }

    @Test
    public void PostController_GetAllTags_ReceivedPostsDto() throws Exception{
        List<PostResponseDto> postResponseDtoList = new ArrayList<>(Collections.singletonList(postResponseDto));

        when(postServiceProd.getPosts()).thenReturn(postResponseDtoList);

        ResultActions response = mockMvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].content").value(postResponseDto.getContent()))
                .andExpect(jsonPath("$[0].title").value(postResponseDto.getTitle()))
                .andExpect(jsonPath("$[0].tags[*].name", containsInAnyOrder("Dog", "Cat")));

    }

    @Test
    public void PostController_DeletePostById_ReturnString() throws Exception{
        doNothing().when(postServiceProd).deletePostById(postId);

        ResultActions response = mockMvc.perform(delete("/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNoContent());
    }

    @Test
    public void PostController_UpdatePostById_ReturnPostDto() throws Exception{
        when(postServiceProd.updatePostById(postRequestDto, postId))
                .thenReturn(postResponseDto);

        ResultActions response = mockMvc.perform(put("/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequestDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.title", CoreMatchers.is(postRequestDto.getTitle())))
                .andExpect(jsonPath("$.content", CoreMatchers.is(postRequestDto.getContent())))
                .andExpect(jsonPath("$.tags[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$.tags[*].name", containsInAnyOrder("Dog", "Cat")));
    }
}
