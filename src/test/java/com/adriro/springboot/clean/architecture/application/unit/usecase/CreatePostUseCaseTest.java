package com.adriro.springboot.clean.architecture.application.unit.usecase;

import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.application.usecase.CreatePostUseCase;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePostUseCaseTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private CreatePostUseCase createPostUseCase;

    private Post post;

    @BeforeEach
    void setUp() {
        post = new Post(1L, "Test Title", "Test Content");
    }

    @Test
    void testExecute() {
        when(postService.save(any(Post.class))).thenReturn(post);

        Post result = createPostUseCase.execute(post);

        assertEquals(post, result);
        verify(postService, times(1)).save(post);
    }
}
