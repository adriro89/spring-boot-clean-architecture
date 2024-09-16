package com.adriro.springboot.clean.architecture.domain.repository;

import com.adriro.springboot.clean.architecture.infrastructure.persistence.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostRepository {
    Page<PostEntity> findAll(Pageable pageable);

    Optional<PostEntity> findById(Long id);

    PostEntity save(PostEntity postEntity);

    void deleteById(Long id);

    boolean existsById(Long id);
}
