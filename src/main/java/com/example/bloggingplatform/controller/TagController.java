package com.example.bloggingplatform.controller;

import com.example.bloggingplatform.dto.requestDto.TagRequestDto;
import com.example.bloggingplatform.dto.responseDto.TagResponseDto;
import com.example.bloggingplatform.service.prod.TagServiceProd;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tags")
@Tag(name = "Tag Controller", description = "Operations with tags")
public class TagController {
    private final TagServiceProd tagServiceProd;

    public TagController(TagServiceProd tagServiceProd) {
        this.tagServiceProd = tagServiceProd;
    }

    @PostMapping
    @Operation(summary = "Creating tag")
    public ResponseEntity<TagResponseDto> createTag(@Valid @RequestBody TagRequestDto tagRequestDto){
        TagResponseDto createdTag = tagServiceProd.createTag(tagRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTag);
    }
    @PutMapping
    @Operation(summary = "Updating tag by id")
    public ResponseEntity<TagResponseDto> updateTagById(@Valid @RequestBody TagRequestDto tagRequestDto, Long id){
        TagResponseDto updatedTag = tagServiceProd.updateTagById(tagRequestDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTag);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Getting tag by id")
    public ResponseEntity<TagResponseDto> getTagById(@PathVariable Long id){
        TagResponseDto receivedTag = tagServiceProd.getTagById(id);
        return ResponseEntity.status(HttpStatus.OK).body(receivedTag);
    }
    @GetMapping
    @Operation(summary = "Getting all tags")
    public ResponseEntity<List<TagResponseDto>> getTags(){
        List<TagResponseDto> receivedTags = tagServiceProd.getTags();
        return ResponseEntity.status(HttpStatus.OK).body(receivedTags);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove tag by id")
    public ResponseEntity<Void> deleteTagById(@PathVariable Long id){
        tagServiceProd.deleteTagById(id);
        return ResponseEntity.noContent().build();
    }
}
