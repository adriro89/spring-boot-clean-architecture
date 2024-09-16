package com.adriro.springboot.clean.architecture.application.unit.service;

import com.adriro.springboot.clean.architecture.application.mapper.PostMapper;
import com.adriro.springboot.clean.architecture.domain.repository.PostRepository;
import com.adriro.springboot.clean.architecture.application.service.PostServiceImpl;
import com.adriro.springboot.clean.architecture.domain.exception.PostNotFoundException;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import com.adriro.springboot.clean.architecture.infrastructure.persistence.entity.PostEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostServiceImpl postService;

    private Post post;
    private PostEntity postEntity;

    @BeforeEach
    void setUp() {
        post = new Post(1L, "Test Title", "Test Content");
        postEntity = new PostEntity(1L, "Test Title", "Test Content");
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PostEntity> postEntityPage = new PageImpl<>(Collections.singletonList(postEntity));
        when(postRepository.findAll(pageable)).thenReturn(postEntityPage);
        when(postMapper.postEntityToPost(postEntity)).thenReturn(post);

        Page<Post> result = postService.findAll(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(postRepository, times(1)).findAll(pageable);
        verify(postMapper, times(1)).postEntityToPost(postEntity);
    }

    @Test
    void testFindByIdSuccess() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(postEntity));
        when(postMapper.postEntityToPost(postEntity)).thenReturn(post);

        Post result = postService.findById(1L);

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());
        verify(postRepository, times(1)).findById(1L);
        verify(postMapper, times(1)).postEntityToPost(postEntity);
    }

    @Test
    void testFindByIdNotFound() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.findById(1L));

        verify(postRepository, times(1)).findById(1L);
        verify(postMapper, times(0)).postEntityToPost(any());
    }

    @Test
    void testSave() {
        when(postMapper.postToPostEntity(post)).thenReturn(postEntity);
        when(postRepository.save(postEntity)).thenReturn(postEntity);
        when(postMapper.postEntityToPost(postEntity)).thenReturn(post);

        Post result = postService.save(post);

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());
        verify(postMapper, times(1)).postToPostEntity(post);
        verify(postRepository, times(1)).save(postEntity);
        verify(postMapper, times(1)).postEntityToPost(postEntity);
    }

    @Test
    void testDeleteByIdSuccess() {
        when(postRepository.existsById(anyLong())).thenReturn(true);

        postService.deleteById(1L);

        verify(postRepository, times(1)).existsById(1L);
        verify(postRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(postRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(PostNotFoundException.class, () -> postService.deleteById(1L));

        verify(postRepository, times(1)).existsById(1L);
        verify(postRepository, times(0)).deleteById(1L);
    }

    @Test
    void testUpdateSuccess() {
        when(postRepository.existsById(anyLong())).thenReturn(true);
        when(postMapper.postToPostEntity(post)).thenReturn(postEntity);
        when(postRepository.save(postEntity)).thenReturn(postEntity);
        when(postMapper.postEntityToPost(postEntity)).thenReturn(post);

        Post result = postService.update(1L, post);

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());
        verify(postRepository, times(1)).existsById(1L);
        verify(postMapper, times(1)).postToPostEntity(post);
        verify(postRepository, times(1)).save(postEntity);
        verify(postMapper, times(1)).postEntityToPost(postEntity);
    }

    @Test
    void testUpdateNotFound() {
        when(postRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(PostNotFoundException.class, () -> postService.update(1L, post));

        verify(postRepository, times(1)).existsById(1L);
        verify(postMapper, times(0)).postToPostEntity(any());
        verify(postRepository, times(0)).save(any());
    }
}
