package com.adriro.springboot.clean.architecture.application.usecase;


import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreatePostUseCase {

    private final PostService postService;

    public Post execute(Post post) {
        return postService.save(post);
    }
}
