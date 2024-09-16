package com.adriro.springboot.clean.architecture.domain.service;

import com.adriro.springboot.clean.architecture.domain.model.Post;
import org.springframework.data.domain.Page;

public interface PostService {
    Page<Post> findAll(Integer page, Integer size);

    Post findById(Long id);

    Post save(Post post);

    void deleteById(Long id);

    Post update(Long id, Post updatedPost);
}
