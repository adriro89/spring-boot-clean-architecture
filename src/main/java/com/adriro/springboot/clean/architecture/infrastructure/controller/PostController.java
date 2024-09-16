package com.adriro.springboot.clean.architecture.infrastructure.controller;

import com.adriro.springboot.clean.architecture.api.PostsApi;
import com.adriro.springboot.clean.architecture.api.model.PostPageResponseDto;
import com.adriro.springboot.clean.architecture.api.model.PostRequestDto;
import com.adriro.springboot.clean.architecture.api.model.PostResponseDto;
import com.adriro.springboot.clean.architecture.application.mapper.PostDtoMapper;
import com.adriro.springboot.clean.architecture.application.usecase.*;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class PostController implements PostsApi {

    private final CreatePostUseCase createPostUseCase;
    private final GetAllPostsUseCase getAllPostsUseCase;
    private final GetPostByIdUseCase getPostByIdUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final DeletePostByIdUseCase deletePostByIdUseCase;
    private final PostDtoMapper postDtoMapper;

    @Override
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto postRequestDto) {
        Post post = postDtoMapper.postRequestDtoToPost(postRequestDto);
        Post createdPost = createPostUseCase.execute(post);
        PostResponseDto postResponseDto = postDtoMapper.postToPostResponseDto(createdPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDto);
    }

    @Override
    public ResponseEntity<Void> deletePostById(@PathVariable Long id) {
        deletePostByIdUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PostPageResponseDto> getAllPosts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        Page<Post> postPage = getAllPostsUseCase.execute(page, size);
        PostPageResponseDto response = postDtoMapper.pageToPostPageResponseDto(postPage);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        Post post = getPostByIdUseCase.execute(id);
        PostResponseDto postResponseDto = postDtoMapper.postToPostResponseDto(post);
        return ResponseEntity.ok(postResponseDto);
    }

    @Override
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id,
            @RequestBody PostRequestDto postRequestDto) {
        Post updatedPost = postDtoMapper.postRequestDtoToPost(postRequestDto);
        Post updatedPostDomain = updatePostUseCase.execute(id, updatedPost);
        PostResponseDto postResponseDto = postDtoMapper.postToPostResponseDto(updatedPostDomain);
        return ResponseEntity.ok(postResponseDto);
    }
}
