package com.adriro.springboot.clean.architecture.application.integration.usecase;

import com.adriro.springboot.clean.architecture.domain.repository.PostRepository;
import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.application.usecase.DeletePostByIdUseCase;
import com.adriro.springboot.clean.architecture.infrastructure.persistence.entity.PostEntity;
import com.adriro.springboot.clean.architecture.utils.TestContainersBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class DeleteByIdUseCaseIT extends TestContainersBase {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    private DeletePostByIdUseCase deletePostByIdUseCase;

    private PostEntity postEntity;

    @BeforeEach
    public void setUp() {
        super.setUp();
        deletePostByIdUseCase = new DeletePostByIdUseCase(postService);
        postEntity = new PostEntity(null, "Test Title", "Test Content");
        postEntity = postRepository.save(postEntity);
    }

    @Test
    void testExecute() {
        Long postId = postEntity.getId();
        deletePostByIdUseCase.execute(postId);
        Optional<PostEntity> deletedPost = postRepository.findById(postId);
        assertFalse(deletedPost.isPresent());
    }
}
