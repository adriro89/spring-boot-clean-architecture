package com.adriro.springboot.clean.architecture.application.unit.usecase;

import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.application.usecase.DeletePostByIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeletePostByIdUseCaseTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private DeletePostByIdUseCase deletePostByIdUseCase;

    private Long postId;

    @BeforeEach
    void setUp() {
        postId = 1L;
    }

    @Test
    void testExecute() {
        deletePostByIdUseCase.execute(postId);

        verify(postService, times(1)).deleteById(postId);
    }
}
