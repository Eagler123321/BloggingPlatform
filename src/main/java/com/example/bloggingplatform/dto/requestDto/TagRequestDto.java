package com.example.bloggingplatform.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Create a new tag", example = """
{
  "name": "Java"
}
""")
@Data
public class TagRequestDto {
    private String name;
}
