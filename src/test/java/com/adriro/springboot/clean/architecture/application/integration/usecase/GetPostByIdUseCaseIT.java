package com.adriro.springboot.clean.architecture.application.integration.usecase;

import com.adriro.springboot.clean.architecture.domain.repository.PostRepository;
import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.application.usecase.GetPostByIdUseCase;
import com.adriro.springboot.clean.architecture.domain.exception.PostNotFoundException;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import com.adriro.springboot.clean.architecture.infrastructure.persistence.entity.PostEntity;
import com.adriro.springboot.clean.architecture.utils.TestContainersBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class GetPostByIdUseCaseIT extends TestContainersBase {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    private GetPostByIdUseCase getPostByIdUseCase;

    private PostEntity postEntity;

    @BeforeEach
    public void setUp() {
        super.setUp();
        getPostByIdUseCase = new GetPostByIdUseCase(postService);
        postEntity = new PostEntity(null, "Test Title", "Test Content");
        postEntity = postRepository.save(postEntity);
    }

    @Test
    void testExecute() {
        Long postId = postEntity.getId();
        Post post = getPostByIdUseCase.execute(postId);

        assertNotNull(post);
        assertEquals(postId, post.getId());
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Content", post.getContent());
    }

    @Test
    void testExecutePostNotFound() {
        Long nonExistentId = 999L;

        assertThrows(PostNotFoundException.class, () -> getPostByIdUseCase.execute(nonExistentId));
    }
}
