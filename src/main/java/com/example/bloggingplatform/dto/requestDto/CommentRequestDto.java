package com.example.bloggingplatform.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Create a new comment", example = """
{
  "content": "Great post!",
  "postId": "1"
}
""")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CommentRequestDto {
    private String content;

    private Long postId;
}
