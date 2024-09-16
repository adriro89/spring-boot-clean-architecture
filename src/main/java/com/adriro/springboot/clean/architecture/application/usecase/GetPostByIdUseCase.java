package com.adriro.springboot.clean.architecture.application.usecase;


import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetPostByIdUseCase {

    private final PostService postService;

    public Post execute(Long id) {
        return postService.findById(id);
    }
}
