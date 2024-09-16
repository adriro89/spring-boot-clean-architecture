package com.adriro.springboot.clean.architecture.application.integration.usecase;

import com.adriro.springboot.clean.architecture.domain.repository.PostRepository;
import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.application.usecase.UpdatePostUseCase;
import com.adriro.springboot.clean.architecture.domain.exception.PostNotFoundException;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import com.adriro.springboot.clean.architecture.infrastructure.persistence.entity.PostEntity;
import com.adriro.springboot.clean.architecture.utils.TestContainersBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UpdatePostUseCaseIT extends TestContainersBase {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    private UpdatePostUseCase updatePostUseCase;

    private PostEntity postEntity;

    @BeforeEach
    public void setUp() {
        super.setUp();
        updatePostUseCase = new UpdatePostUseCase(postService);
        postEntity = new PostEntity(null, "Original Title", "Original Content");
        postEntity = postRepository.save(postEntity);
    }

    @Test
    void testExecute() {
        Long postId = postEntity.getId();
        Post updatedPost = new Post(postId, "Updated Title", "Updated Content");

        Post result = updatePostUseCase.execute(postId, updatedPost);

        assertNotNull(result);
        assertEquals(postId, result.getId());
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Content", result.getContent());

        Optional<PostEntity> updatedPostEntity = postRepository.findById(postId);
        assertTrue(updatedPostEntity.isPresent());
        assertEquals("Updated Title", updatedPostEntity.get().getTitle());
        assertEquals("Updated Content", updatedPostEntity.get().getContent());
    }

    @Test
    void testExecutePostNotFound() {
        Long nonExistentId = 999L;
        Post updatedPost = new Post(nonExistentId, "Updated Title", "Updated Content");

        assertThrows(PostNotFoundException.class, () -> updatePostUseCase.execute(nonExistentId, updatedPost));
    }
}
