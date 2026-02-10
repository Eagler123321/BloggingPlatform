package com.example.bloggingplatform.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Create a new comment", example = """
{
  "content": "Great post!",
  "postId": "1"
}
""")
@Data
public class CommentRequestDto {
    private String content;

    private Long postId;
}
