package com.adriro.springboot.clean.architecture.infrastructure.unit.repository;

import com.adriro.springboot.clean.architecture.infrastructure.persistence.entity.PostEntity;
import com.adriro.springboot.clean.architecture.infrastructure.persistence.repository.PostJpaRepository;
import com.adriro.springboot.clean.architecture.infrastructure.persistence.repository.PostRepositoryImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostRepositoryImplTest {

    @Mock
    private PostJpaRepository postJpaRepository;

    @InjectMocks
    private PostRepositoryImpl postRepositoryImpl;

    private PostEntity postEntity;
    private Page<PostEntity> postPage;
    private Long postId;

    @BeforeEach
    void setUp() {
        postId = 1L;
        postEntity = new PostEntity(postId, "Test Title", "Test Content");
        postPage = new PageImpl<>(Collections.singletonList(postEntity), PageRequest.of(0, 10), 1);
    }

    @Test
    void testFindAll() {
        when(postJpaRepository.findAll(any(Pageable.class))).thenReturn(postPage);

        Page<PostEntity> result = postRepositoryImpl.findAll(PageRequest.of(0, 10));

        assertEquals(postPage, result);
        verify(postJpaRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testFindById() {
        when(postJpaRepository.findById(anyLong())).thenReturn(Optional.of(postEntity));

        Optional<PostEntity> result = postRepositoryImpl.findById(postId);

        assertTrue(result.isPresent());
        assertEquals(postEntity, result.get());
        verify(postJpaRepository, times(1)).findById(postId);
    }

    @Test
    void testSave() {
        when(postJpaRepository.save(any(PostEntity.class))).thenReturn(postEntity);

        PostEntity result = postRepositoryImpl.save(postEntity);

        assertEquals(postEntity, result);
        verify(postJpaRepository, times(1)).save(postEntity);
    }

    @Test
    void testDeleteById() {
        doNothing().when(postJpaRepository).deleteById(anyLong());

        postRepositoryImpl.deleteById(postId);

        verify(postJpaRepository, times(1)).deleteById(postId);
    }

    @Test
    void testExistsById() {
        when(postJpaRepository.existsById(anyLong())).thenReturn(true);

        boolean result = postRepositoryImpl.existsById(postId);

        assertTrue(result);
        verify(postJpaRepository, times(1)).existsById(postId);
    }
}
