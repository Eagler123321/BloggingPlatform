package com.example.bloggingplatform.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Create a new tag", example = """
{
  "name": "Java"
}
""")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TagRequestDto {
    private String name;
}
