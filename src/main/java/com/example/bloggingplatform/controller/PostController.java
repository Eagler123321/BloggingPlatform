package com.example.bloggingplatform.controller;

import com.example.bloggingplatform.dto.requestDto.PostRequestDto;
import com.example.bloggingplatform.dto.responseDto.PostResponseDto;
import com.example.bloggingplatform.service.prod.PostServiceProd;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@Tag(name = "Post Controller", description = "Operations with posts")
public class PostController {
    private final PostServiceProd postServiceProd;

    public PostController(PostServiceProd postServiceProd) {
        this.postServiceProd = postServiceProd;
    }

    @PostMapping
    @Operation(summary = "Creating post")
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto postRequestDto){
        PostResponseDto createdPost = postServiceProd.createPost(postRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Updating post by id")
    public ResponseEntity<PostResponseDto> updatePostById(@Valid @RequestBody PostRequestDto postRequestDto, @PathVariable Long id){
         PostResponseDto updatedPost = postServiceProd.updatePostById(postRequestDto, id);
         return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Getting post by id")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id){
        PostResponseDto receivedPost = postServiceProd.getPostById(id);
        return ResponseEntity.status(HttpStatus.OK).body(receivedPost);
    }
    @GetMapping
    @Operation(summary = "Getting all posts")
    public ResponseEntity<List<PostResponseDto>> getPosts(){
        List<PostResponseDto> receivedPosts = postServiceProd.getPosts();
        return ResponseEntity.status(HttpStatus.OK).body(receivedPosts);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove post by id")
    public ResponseEntity<Void> deletePostById(@PathVariable Long id){
        postServiceProd.deletePostById(id);
        return ResponseEntity.noContent().build();
    }
}
