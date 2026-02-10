package com.example.bloggingplatform.service.prod;

import com.example.bloggingplatform.dto.requestDto.CommentRequestDto;
import com.example.bloggingplatform.dto.responseDto.CommentResponseDto;
import com.example.bloggingplatform.entity.Comment;
import com.example.bloggingplatform.error.NotFoundException;
import com.example.bloggingplatform.mapper.CommentMapper;
import com.example.bloggingplatform.repository.CommentRepository;
import com.example.bloggingplatform.repository.PostRepository;
import com.example.bloggingplatform.service.noimlp.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CommentServiceProd implements CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    public CommentServiceProd(CommentMapper commentMapper, CommentRepository commentRepository, PostRepository postRepository) {
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        log.info("Creating new comment with content: {}", commentRequestDto.getContent());

        Comment newComment = commentMapper.toEntity(commentRequestDto);
        newComment.setCreatedAt(LocalDateTime.now());
        newComment.setPost(postRepository.getReferenceById(commentRequestDto.getPostId()));

        Comment savedComment = commentRepository.save(newComment);

        log.info("Comment created successfully with content: {}", commentRequestDto.getContent());
        return commentMapper.toDto(savedComment);
    }

    @Override
    @Transactional
    public CommentResponseDto updateCommentById(CommentRequestDto commentRequestDto, Long id) {
        log.info("Updating new comment with id: {}", id);
        Comment oldComment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment not found with id" + id));

        oldComment.setContent(commentRequestDto.getContent());

        if (commentRequestDto.getPostId() != null){
            oldComment.setPost(postRepository.getReferenceById(commentRequestDto.getPostId()));
        }

        Comment updatedComment = commentRepository.save(oldComment);

        log.info("Comment updated successfully with id: {}", id);
        return commentMapper.toDto(updatedComment);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponseDto getCommentById(Long id) {
        Comment foundComment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment not found with id" + id));

        return commentMapper.toDto(foundComment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments() {
        List<CommentResponseDto> foundComments = commentRepository.findAll().stream()
                .map(commentMapper::toDto)
                .toList();

        return foundComments;
    }


    @Override
    @Transactional
    public void deleteCommentById(Long id) {
        log.info("Deleting comment with id: {}", id);
        commentRepository.deleteById(id);
        log.info("Comment deleted successfully with id: {}", id);
    }
}
