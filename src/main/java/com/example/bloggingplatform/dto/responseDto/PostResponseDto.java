package com.example.bloggingplatform.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Schema(example = """
{
  "id": 1,
  "title": "My First Post",
  "content": "This is the content...",
  "createdAt": "2026-02-04T10:00:00",
  "tags": [
    { "id": 1, "name": "Java" },
    { "id": 2, "name": "Spring" }
  ]
}
""")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PostResponseDto {
    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Set<TagResponseDto> tags;
}
