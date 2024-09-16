package com.adriro.springboot.clean.architecture.infrastructure.persistence.repository;

import com.adriro.springboot.clean.architecture.domain.repository.PostRepository;
import com.adriro.springboot.clean.architecture.infrastructure.persistence.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Page<PostEntity> findAll(Pageable pageable) {
        return postJpaRepository.findAll(pageable);
    }

    @Override
    public Optional<PostEntity> findById(Long id) {
        return postJpaRepository.findById(id);
    }

    @Override
    public PostEntity save(PostEntity postEntity) {
        return postJpaRepository.save(postEntity);
    }

    @Override
    public void deleteById(Long id) {
        postJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return postJpaRepository.existsById(id);
    }
}
