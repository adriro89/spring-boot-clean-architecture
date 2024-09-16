package com.adriro.springboot.clean.architecture.infrastructure.integration.controller;

import com.adriro.springboot.clean.architecture.application.mapper.PostDtoMapper;
import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.application.usecase.*;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import com.adriro.springboot.clean.architecture.infrastructure.persistence.entity.PostEntity;
import com.adriro.springboot.clean.architecture.infrastructure.persistence.repository.PostJpaRepository;
import com.adriro.springboot.clean.architecture.utils.TestContainersBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PostControllerIT extends TestContainersBase {


    @Autowired
    private PostJpaRepository postJpaRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostDtoMapper postDtoMapper;

    @Autowired
    private CreatePostUseCase createPostUseCase;

    @Autowired
    private GetAllPostsUseCase getAllPostsUseCase;

    @Autowired
    private GetPostByIdUseCase getPostByIdUseCase;

    @Autowired
    private UpdatePostUseCase updatePostUseCase;

    @Autowired
    private DeletePostByIdUseCase deletePostByIdUseCase;

    @BeforeEach
    public void setUp() {
        super.setUp();
        postJpaRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertNotNull(postJpaRepository);
        assertNotNull(postService);
        assertNotNull(postDtoMapper);
        assertNotNull(createPostUseCase);
        assertNotNull(getAllPostsUseCase);
        assertNotNull(getPostByIdUseCase);
        assertNotNull(updatePostUseCase);
        assertNotNull(deletePostByIdUseCase);
    }

    @Test
    void testCreatePostUseCase() {
        Post post = new Post(null, "Test Title", "Test Content");
        Post createdPost = createPostUseCase.execute(post);

        assertNotNull(createdPost.getId());
        assertEquals("Test Title", createdPost.getTitle());
        assertEquals("Test Content", createdPost.getContent());
    }

    @Test
    void testGetAllPostsUseCase() {
        Post post = new Post(null, "Test Title", "Test Content");
        createPostUseCase.execute(post);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> postPage = getAllPostsUseCase.execute(0, 10);

        assertTrue(postPage.hasContent());
        assertEquals(1, postPage.getTotalElements());
    }

    @Test
    void testGetPostByIdUseCase() {
        Post post = new Post(null, "Test Title", "Test Content");
        Post createdPost = createPostUseCase.execute(post);

        Post fetchedPost = getPostByIdUseCase.execute(createdPost.getId());

        assertEquals(createdPost.getId(), fetchedPost.getId());
        assertEquals(createdPost.getTitle(), fetchedPost.getTitle());
        assertEquals(createdPost.getContent(), fetchedPost.getContent());
    }

    @Test
    void testUpdatePostUseCase() {
        Post post = new Post(null, "Test Title", "Test Content");
        Post createdPost = createPostUseCase.execute(post);

        Post updatedPost = new Post(createdPost.getId(), "Updated Title", "Updated Content");
        Post resultPost = updatePostUseCase.execute(createdPost.getId(), updatedPost);

        assertEquals(createdPost.getId(), resultPost.getId());
        assertEquals("Updated Title", resultPost.getTitle());
        assertEquals("Updated Content", resultPost.getContent());
    }

    @Test
    void testDeletePostUseCase() {
        Post post = new Post(null, "Test Title", "Test Content");
        Post createdPost = createPostUseCase.execute(post);

        deletePostByIdUseCase.execute(createdPost.getId());

        Optional<PostEntity> deletedPost = postJpaRepository.findById(createdPost.getId());
        assertTrue(deletedPost.isEmpty());
    }

    @Test
    void testGetPostByIdNotFound() {
        Long nonExistentId = 999L;

        assertThrows(RuntimeException.class, () -> {
            getPostByIdUseCase.execute(nonExistentId);
        });
    }

    @Test
    void testUpdatePostNotFound() {
        Post nonExistentPost = new Post(null, "Updated Title", "Updated Content");

        assertThrows(RuntimeException.class, () -> {
            updatePostUseCase.execute(999L, nonExistentPost);
        });
    }

    @Test
    void testDeletePostNotFound() {
        assertThrows(RuntimeException.class, () -> {
            deletePostByIdUseCase.execute(999L);
        });
    }
}
