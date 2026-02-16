package com.example.bloggingplatform.controller;

import com.example.bloggingplatform.dto.requestDto.TagRequestDto;
import com.example.bloggingplatform.dto.responseDto.TagResponseDto;
import com.example.bloggingplatform.service.prod.TagServiceProd;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = TagController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TagControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private TagServiceProd tagServiceProd;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    private TagRequestDto tagRequestDto;
    private TagResponseDto tagResponseDto;

    private final String name = "cat";
    private final Long tagId = 5L;

    @BeforeEach
    public void init(){
        tagRequestDto = TagRequestDto.builder().name(name).build();
        tagResponseDto = TagResponseDto.builder().id(tagId).name(name).build();
    }

    @Test
    public void TagController_CreateTag_CreatedTag() throws Exception{
        given(tagServiceProd.createTag(any(TagRequestDto.class)))
                .willReturn(tagResponseDto);

        ResultActions response = mockMvc.perform(post("/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagRequestDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", CoreMatchers.is(tagRequestDto.getName())));
    }

    @Test
    public void TagController_GetTagById_ReceivedTag() throws Exception{
        when(tagServiceProd.getTagById(tagId)).thenReturn(tagResponseDto);

        ResultActions response = mockMvc.perform(get("/tags/{id}", tagId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is(tagResponseDto.getName())));
    }

    @Test
    public void TagController_GetAllTags_ReceivedTagsDto() throws Exception{
        Set<TagResponseDto> tagResponseDtoList = new LinkedHashSet<>(Collections.singletonList(tagResponseDto));

        when(tagServiceProd.getTags()).thenReturn((List<TagResponseDto>) tagResponseDtoList);

        ResultActions response = mockMvc.perform(get("/tags")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value(tagResponseDto.getName()));
    }

    @Test
    public void TagController_DeleteTagById_ReturnString() throws Exception{
        doNothing().when(tagServiceProd).deleteTagById(tagId);

        ResultActions response = mockMvc.perform(delete("/tags/{id}", tagId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNoContent());
    }

    @Test
    public void TagController_UpdateTagById_ReturnTagDto() throws Exception{
        when(tagServiceProd.updateTagById(tagRequestDto, tagId))
                .thenReturn(tagResponseDto);

        ResultActions response = mockMvc.perform(put("/tags/{id}", tagId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagRequestDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is(tagRequestDto.getName())));
    }
}
