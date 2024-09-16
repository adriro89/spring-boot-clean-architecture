package com.adriro.springboot.clean.architecture.application.unit.usecase;

import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.application.usecase.GetAllPostsUseCase;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllPostsUseCaseTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private GetAllPostsUseCase getAllPostsUseCase;

    private Page<Post> postPage;

    @BeforeEach
    void setUp() {
        Post post = new Post(1L, "Test Title", "Test Content");
        postPage = new PageImpl<>(Collections.singletonList(post), PageRequest.of(0, 10), 1);
    }

    @Test
    void testExecute() {
        when(postService.findAll(anyInt(), anyInt())).thenReturn(postPage);

        Page<Post> result = getAllPostsUseCase.execute(0, 10);

        assertNotNull(result);
        assertEquals(postPage.getTotalElements(), result.getTotalElements());
        assertEquals(postPage.getContent().getFirst().getTitle(), result.getContent().getFirst().getTitle());
        assertEquals(postPage.getContent().getFirst().getContent(), result.getContent().getFirst().getContent());

        verify(postService, times(1)).findAll(0, 10);
    }
}
