package com.adriro.springboot.clean.architecture.application.unit.mapper;

import com.adriro.springboot.clean.architecture.application.mapper.PostMapper;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import com.adriro.springboot.clean.architecture.infrastructure.persistence.entity.PostEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostMapperTest {

    private PostMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(PostMapper.class);
    }

    @Test
    void testPostToPostEntity() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test Title");
        post.setContent("Test Content");

        PostEntity postEntity = mapper.postToPostEntity(post);

        assertNotNull(postEntity);
        assertEquals(post.getId(), postEntity.getId());
        assertEquals(post.getTitle(), postEntity.getTitle());
        assertEquals(post.getContent(), postEntity.getContent());
    }

    @Test
    void testPostEntityToPost() {
        PostEntity postEntity = new PostEntity();
        postEntity.setId(1L);
        postEntity.setTitle("Test Title");
        postEntity.setContent("Test Content");

        Post post = mapper.postEntityToPost(postEntity);

        assertNotNull(post);
        assertEquals(postEntity.getId(), post.getId());
        assertEquals(postEntity.getTitle(), post.getTitle());
        assertEquals(postEntity.getContent(), post.getContent());
    }

    @Test
    void testPostToPostEntityWithNull() {
        PostEntity postEntity = mapper.postToPostEntity(null);

        assertNull(postEntity);
    }

    @Test
    void testPostEntityToPostWithNull() {
        Post post = mapper.postEntityToPost(null);

        assertNull(post);
    }
}
