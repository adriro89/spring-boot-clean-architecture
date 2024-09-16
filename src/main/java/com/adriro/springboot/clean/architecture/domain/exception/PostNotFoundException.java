package com.adriro.springboot.clean.architecture.domain.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(Long id) {
        super("Post with ID " + id + " not found.");
    }
}
