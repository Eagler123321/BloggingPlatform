package com.example.bloggingplatform.service.noimlp;

import com.example.bloggingplatform.dto.requestDto.CommentRequestDto;
import com.example.bloggingplatform.dto.responseDto.CommentResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentResponseDto createComment(CommentRequestDto commentRequestDto);

    CommentResponseDto updateCommentById(CommentRequestDto commentRequestDto, Long id);

    CommentResponseDto getCommentById(Long id);

    List<CommentResponseDto> getComments();

    void deleteCommentById(Long id);
}
