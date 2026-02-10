package com.example.bloggingplatform.service.noimlp;

import com.example.bloggingplatform.dto.requestDto.PostRequestDto;
import com.example.bloggingplatform.dto.responseDto.PostResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostResponseDto createPost(PostRequestDto postRequestDto);

    PostResponseDto updatePostById(PostRequestDto postRequestDto, Long id);

    PostResponseDto getPostById(Long id);

    List<PostResponseDto> getPosts();

    void deletePostById(Long id);
}
