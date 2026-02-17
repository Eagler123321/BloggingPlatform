package com.example.bloggingplatform.controller;

import com.example.bloggingplatform.dto.requestDto.CommentRequestDto;
import com.example.bloggingplatform.dto.responseDto.CommentResponseDto;
import com.example.bloggingplatform.service.prod.CommentServiceProd;
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
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CommentServiceProd commentServiceProd;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private CommentRequestDto commentRequestDto;
    private CommentResponseDto commentResponseDto;

    private final String content = "ohh my";
    private final Long postId = 1L;
    private final Long commentId = 2L;
    private final LocalDateTime now = LocalDateTime.now();


    @BeforeEach
    public void init(){
        commentRequestDto = CommentRequestDto.builder().content(content).postId(postId).build();

        commentResponseDto = CommentResponseDto.builder()
                .id(commentId)
                .content(content)
                .postId(postId)
                .createdAt(now)
                .build();
    }

    @Test
    public void CommentController_CreateComment_CreatedComment() throws Exception{
        given(commentServiceProd.createComment(any(CommentRequestDto.class)))
                .willReturn(commentResponseDto);

        ResultActions response = mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentRequestDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.content", CoreMatchers.is(commentRequestDto.getContent())))
                .andExpect(jsonPath("$.postId").value(commentRequestDto.getPostId()))
        ;
    }

    @Test
    public void CommentController_GetCommentById_ReceivedComment() throws Exception{
        when(commentServiceProd.getCommentById(commentId)).thenReturn(commentResponseDto);

        ResultActions response = mockMvc.perform(get("/comments/{id}", commentId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(commentResponseDto.getPostId()))
                .andExpect(jsonPath("$.content", CoreMatchers.is(commentResponseDto.getContent())));
    }

    @Test
    public void CommentController_GetAllComments_ReceivedCommentsDto() throws Exception{
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>(Collections.singletonList(commentResponseDto));

        when(commentServiceProd.getComments()).thenReturn(commentResponseDtoList);

        ResultActions response = mockMvc.perform(get("/comments")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].content").value(commentResponseDto.getContent()))
                .andExpect(jsonPath("$[0].postId").value(commentResponseDto.getPostId()));

    }

    @Test
    public void CommentController_DeleteCommentById_ReturnString() throws Exception{
        doNothing().when(commentServiceProd).deleteCommentById(commentId);

        ResultActions response = mockMvc.perform(delete("/comments/{id}", commentId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNoContent());
    }

    @Test
    public void CommentController_UpdateCommentById_ReturnCommentDto() throws Exception{
        when(commentServiceProd.updateCommentById(commentRequestDto, commentId))
                .thenReturn(commentResponseDto);

        ResultActions response = mockMvc.perform(put("/comments/{id}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentRequestDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(commentRequestDto.getPostId()))
                .andExpect(jsonPath("$.content", CoreMatchers.is(commentRequestDto.getContent())));
    }
}
