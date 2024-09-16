package com.adriro.springboot.clean.architecture.application.usecase;


import com.adriro.springboot.clean.architecture.domain.service.PostService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeletePostByIdUseCase {

    private final PostService postService;

    public void execute(Long id) {
        postService.deleteById(id);
    }
}
