package com.adriro.springboot.clean.architecture.application.integration.usecase;

import com.adriro.springboot.clean.architecture.domain.repository.PostRepository;
import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.application.usecase.GetAllPostsUseCase;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import com.adriro.springboot.clean.architecture.infrastructure.persistence.entity.PostEntity;
import com.adriro.springboot.clean.architecture.utils.TestContainersBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetAllPostsUseCaseIT extends TestContainersBase {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    private GetAllPostsUseCase getAllPostsUseCase;

    @BeforeEach
    public void setUp() {
        super.setUp();
        getAllPostsUseCase = new GetAllPostsUseCase(postService);
        PostEntity postEntity1 = new PostEntity(null, "Test Title 1", "Test Content 1");
        PostEntity postEntity2 = new PostEntity(null, "Test Title 2", "Test Content 2");
        postRepository.save(postEntity1);
        postRepository.save(postEntity2);
    }

    @Test
    void testExecute() {
        Page<Post> postsPage = getAllPostsUseCase.execute(0, 10);

        assertNotNull(postsPage);
        assertEquals(2, postsPage.getTotalElements());

        List<Post> posts = postsPage.getContent();
        assertFalse(posts.isEmpty());
        assertEquals(2, posts.size());
        assertEquals("Test Title 1", posts.get(0).getTitle());
        assertEquals("Test Title 2", posts.get(1).getTitle());
    }

    @Test
    void testExecutePagination() {
        Page<Post> firstPage = getAllPostsUseCase.execute(0, 1);
        Page<Post> secondPage = getAllPostsUseCase.execute(1, 1);

        assertNotNull(firstPage);
        assertEquals(1, firstPage.getNumberOfElements());
        assertEquals(2, firstPage.getTotalElements());

        assertNotNull(secondPage);
        assertEquals(1, secondPage.getNumberOfElements());
        assertEquals(2, secondPage.getTotalElements());

        assertEquals("Test Title 1", firstPage.getContent().getFirst().getTitle());
        assertEquals("Test Title 2", secondPage.getContent().getFirst().getTitle());
    }
}
