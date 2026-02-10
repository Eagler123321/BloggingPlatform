package com.example.bloggingplatform.service.prod;

import com.example.bloggingplatform.dto.requestDto.PostRequestDto;
import com.example.bloggingplatform.dto.responseDto.PostResponseDto;
import com.example.bloggingplatform.entity.Post;
import com.example.bloggingplatform.mapper.PostMapper;
import com.example.bloggingplatform.repository.PostRepository;
import com.example.bloggingplatform.repository.TagRepository;
import com.example.bloggingplatform.service.noimlp.PostService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
public class PostServiceProd implements PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostMapper postMapper;

    public PostServiceProd(PostRepository postRepository, TagRepository tagRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.postMapper = postMapper;
    }

    @Override
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post newPost = postMapper.toEntity(postRequestDto);
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setUpdatedAt(LocalDateTime.now());

        if (postRequestDto.getTagIds() != null && !postRequestDto.getTagIds().isEmpty()){
            newPost.setTags(new HashSet<>(tagRepository.findAllById(postRequestDto.getTagIds())));
        }

        Post savedPost = postRepository.save(newPost);
        return postMapper.toDto(savedPost);
    }

    @Override
    @Transactional
    public PostResponseDto updatePostById(PostRequestDto postRequestDto, Long id) {
        Post oldPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id" + id));

        oldPost.setTitle(postRequestDto.getTitle());
        oldPost.setContent(postRequestDto.getContent());
        oldPost.setUpdatedAt(LocalDateTime.now());

        if (postRequestDto.getTagIds() != null && !postRequestDto.getTagIds().isEmpty()) {
            oldPost.setTags(new HashSet<>(tagRepository.findAllById(postRequestDto.getTagIds())));
        }

        Post updatedPost = postRepository.save(oldPost);

        return postMapper.toDto(updatedPost);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long id) {
        Post foundPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id" + id));

        return postMapper.toDto(foundPost);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        List<PostResponseDto> foundPosts = postRepository.findAll().stream()
                .map(postMapper::toDto)
                .toList();

        return foundPosts;
    }

    @Override
    @Transactional
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

}
