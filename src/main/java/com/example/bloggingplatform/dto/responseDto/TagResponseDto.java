package com.example.bloggingplatform.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(example = """
{
  "id": 1,
  "name": "Java"
}
""")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TagResponseDto {
    private Long id;

    private String name;
}
