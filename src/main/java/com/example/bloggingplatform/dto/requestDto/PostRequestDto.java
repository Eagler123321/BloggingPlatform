package com.example.bloggingplatform.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Schema(description = "Create a new post", example = """
{
  "title": "My First Post",
  "content": "This is the content...",
  "tagIds": [1, 2]
}
""")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PostRequestDto {
    private String title;

    private String content;

    @Schema(type = "array", implementation = Long.class)
    private Set<Long> tagIds;
}
