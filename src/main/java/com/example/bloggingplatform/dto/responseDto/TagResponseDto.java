package com.example.bloggingplatform.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(example = """
{
  "id": 1,
  "name": "Java"
}
""")
@Data
public class TagResponseDto {
    private Long id;

    private String name;
}
