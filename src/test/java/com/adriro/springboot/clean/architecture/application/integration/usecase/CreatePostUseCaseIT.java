package com.adriro.springboot.clean.architecture.application.integration.usecase;

import com.adriro.springboot.clean.architecture.domain.repository.PostRepository;
import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.application.usecase.CreatePostUseCase;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import com.adriro.springboot.clean.architecture.infrastructure.persistence.entity.PostEntity;
import com.adriro.springboot.clean.architecture.utils.TestContainersBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CreatePostUseCaseIT extends TestContainersBase {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    private CreatePostUseCase createPostUseCase;

    @BeforeEach
    public void setUp() {
        super.setUp();
        createPostUseCase = new CreatePostUseCase(postService);
    }

    @Test
    void testExecute() {
        Post post = new Post(null, "Integration Test Post", "Integration Test Content");

        Post savedPost = createPostUseCase.execute(post);

        assertNotNull(savedPost);
        assertNotNull(savedPost.getId());

        Optional<PostEntity> foundPostEntity = postRepository.findById(savedPost.getId());
        assertTrue(foundPostEntity.isPresent());
        assertEquals(savedPost.getTitle(), foundPostEntity.get().getTitle());
        assertEquals(savedPost.getContent(), foundPostEntity.get().getContent());
    }
}
