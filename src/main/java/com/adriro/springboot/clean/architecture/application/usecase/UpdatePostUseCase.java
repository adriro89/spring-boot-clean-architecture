package com.adriro.springboot.clean.architecture.application.usecase;

import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdatePostUseCase {

    private final PostService postService;

    public Post execute(Long id, Post updatedPost) {
        return postService.update(id, updatedPost);
    }
}
