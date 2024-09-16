package com.adriro.springboot.clean.architecture.application.usecase;


import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class GetAllPostsUseCase {

    private final PostService postService;

    public Page<Post> execute(Integer page, Integer size) {
        return postService.findAll(page, size);
    }
}
