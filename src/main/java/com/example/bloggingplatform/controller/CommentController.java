package com.example.bloggingplatform.controller;

import com.example.bloggingplatform.dto.requestDto.CommentRequestDto;
import com.example.bloggingplatform.dto.responseDto.CommentResponseDto;
import com.example.bloggingplatform.service.prod.CommentServiceProd;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/comments")
@Tag(name = "Comment Controller", description = "Operations with comments")
public class CommentController {
    private final CommentServiceProd commentService;

    public CommentController(CommentServiceProd commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @Operation(summary = "Creating comment")
    public ResponseEntity<CommentResponseDto> createComment(@Valid @RequestBody CommentRequestDto commentRequestDto){
        CommentResponseDto savedComment = commentService.createComment(commentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }
    @PutMapping( "/{id}")
    @Operation(summary = "Updating comment by id")
    public ResponseEntity<CommentResponseDto> updateCommentById(@Valid @RequestBody CommentRequestDto commentRequestDto, @PathVariable Long id){
        CommentResponseDto updatedComment = commentService.updateCommentById(commentRequestDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedComment);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Getting comment by id")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long id){
        CommentResponseDto receivedComment = commentService.getCommentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(receivedComment);
    }
    @GetMapping
    @Operation(summary = "Getting all comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(){
        List<CommentResponseDto> receivedComments = commentService.getComments();
        return ResponseEntity.status(HttpStatus.OK).body(receivedComments);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove comment by id")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){
        commentService.deleteCommentById(id);
        return ResponseEntity.noContent().build();
    }
}
