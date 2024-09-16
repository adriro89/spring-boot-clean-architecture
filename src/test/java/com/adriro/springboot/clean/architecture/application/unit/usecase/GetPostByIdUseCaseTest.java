package com.adriro.springboot.clean.architecture.application.unit.usecase;

import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.application.usecase.GetPostByIdUseCase;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPostByIdUseCaseTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private GetPostByIdUseCase getPostByIdUseCase;

    private Post post;
    private Long postId;

    @BeforeEach
    void setUp() {
        postId = 1L;
        post = new Post(postId, "Test Title", "Test Content");
    }

    @Test
    void testExecute() {
        when(postService.findById(anyLong())).thenReturn(post);

        Post result = getPostByIdUseCase.execute(postId);

        assertEquals(post, result);
        verify(postService, times(1)).findById(postId);
    }
}
