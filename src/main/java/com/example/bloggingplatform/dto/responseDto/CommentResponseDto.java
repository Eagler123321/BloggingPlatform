package com.example.bloggingplatform.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(example = """
{
  "id": 1,
  "content": "Great post!",
  "createdAt": "2005-04-08T10:00:00",
  "postId": "1"
}
""")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CommentResponseDto {
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    private Long postId;
}
