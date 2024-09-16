package com.adriro.springboot.clean.architecture.application.unit.usecase;

import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.application.usecase.UpdatePostUseCase;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePostUseCaseTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private UpdatePostUseCase updatePostUseCase;

    private Post updatedPost;
    private Post updatedPostResponse;
    private Long postId;

    @BeforeEach
    void setUp() {
        postId = 1L;
        updatedPost = new Post(postId, "Updated Title", "Updated Content");
        updatedPostResponse = new Post(postId, "Updated Title", "Updated Content");
    }

    @Test
    void testExecute() {
        when(postService.update(anyLong(), any(Post.class))).thenReturn(updatedPostResponse);

        Post result = updatePostUseCase.execute(postId, updatedPost);

        assertEquals(updatedPostResponse, result);
        verify(postService, times(1)).update(postId, updatedPost);
    }
}
