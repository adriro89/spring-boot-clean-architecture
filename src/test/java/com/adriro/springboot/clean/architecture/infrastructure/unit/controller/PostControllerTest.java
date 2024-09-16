package com.adriro.springboot.clean.architecture.infrastructure.unit.controller;

import com.adriro.springboot.clean.architecture.api.model.PostPageResponseDto;
import com.adriro.springboot.clean.architecture.api.model.PostRequestDto;
import com.adriro.springboot.clean.architecture.api.model.PostResponseDto;
import com.adriro.springboot.clean.architecture.application.mapper.PostDtoMapper;
import com.adriro.springboot.clean.architecture.application.usecase.*;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import com.adriro.springboot.clean.architecture.infrastructure.controller.PostController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @Mock
    private CreatePostUseCase createPostUseCase;

    @Mock
    private GetAllPostsUseCase getAllPostsUseCase;

    @Mock
    private GetPostByIdUseCase getPostByIdUseCase;

    @Mock
    private UpdatePostUseCase updatePostUseCase;

    @Mock
    private DeletePostByIdUseCase deletePostByIdUseCase;

    @Mock
    private PostDtoMapper postDtoMapper;

    @InjectMocks
    private PostController postController;

    private PostRequestDto postRequestDto;
    private PostResponseDto postResponseDto;
    private Post post;
    private Page<Post> postPage;
    private Long postId;

    @BeforeEach
    void setUp() {
        postId = 1L;
        postRequestDto = new PostRequestDto();
        postRequestDto.setTitle("Test Title");
        postRequestDto.setContent("Test Content");

        postResponseDto = new PostResponseDto();
        postResponseDto.setTitle("Test Title");
        postResponseDto.setContent("Test Content");

        post = new Post(postId, "Test Title", "Test Content");
        postPage = new PageImpl<>(Collections.singletonList(post), PageRequest.of(0, 10), 1);
    }

    @Test
    void testCreatePost() {
        when(postDtoMapper.postRequestDtoToPost(postRequestDto)).thenReturn(post);
        when(createPostUseCase.execute(post)).thenReturn(post);
        when(postDtoMapper.postToPostResponseDto(post)).thenReturn(postResponseDto);

        ResponseEntity<PostResponseDto> response = postController.createPost(postRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(postResponseDto, response.getBody());
    }

    @Test
    void testDeletePostById() {
        doNothing().when(deletePostByIdUseCase).execute(postId);

        ResponseEntity<Void> response = postController.deletePostById(postId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deletePostByIdUseCase).execute(postId);
    }

    @Test
    void testGetAllPosts() {
        when(getAllPostsUseCase.execute(anyInt(), anyInt())).thenReturn(postPage);
        when(postDtoMapper.pageToPostPageResponseDto(postPage)).thenReturn(new PostPageResponseDto());

        ResponseEntity<PostPageResponseDto> response = postController.getAllPosts(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new PostPageResponseDto(), response.getBody());
    }

    @Test
    void testGetPostById() {
        when(getPostByIdUseCase.execute(postId)).thenReturn(post);
        when(postDtoMapper.postToPostResponseDto(post)).thenReturn(postResponseDto);

        ResponseEntity<PostResponseDto> response = postController.getPostById(postId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postResponseDto, response.getBody());
    }

    @Test
    void testUpdatePost() {
        when(postDtoMapper.postRequestDtoToPost(postRequestDto)).thenReturn(post);
        when(updatePostUseCase.execute(anyLong(), any(Post.class))).thenReturn(post);
        when(postDtoMapper.postToPostResponseDto(post)).thenReturn(postResponseDto);

        ResponseEntity<PostResponseDto> response = postController.updatePost(postId, postRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postResponseDto, response.getBody());
    }
}
