package com.example.bloggingplatform.error;

public class UnexpectedException extends RuntimeException {
    public UnexpectedException(String message) {
        super(message);
    }
}
