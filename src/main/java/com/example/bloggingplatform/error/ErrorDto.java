package com.example.bloggingplatform.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorDto {
    private int status;
    private String error;
    private String description;
    private long timestamp;

    public ErrorDto(int status, String error, String description){
        this.status = status;
        this.error = HttpStatus.valueOf(error).getReasonPhrase();
        this.description = description;
        this.timestamp = System.currentTimeMillis();
    }
}
