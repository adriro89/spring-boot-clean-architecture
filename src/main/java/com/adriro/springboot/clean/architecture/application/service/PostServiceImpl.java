package com.adriro.springboot.clean.architecture.application.service;

import com.adriro.springboot.clean.architecture.application.mapper.PostMapper;
import com.adriro.springboot.clean.architecture.domain.repository.PostRepository;
import com.adriro.springboot.clean.architecture.domain.exception.PostNotFoundException;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.infrastructure.persistence.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public Page<Post> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostEntity> postEntities = postRepository.findAll(pageable);
        return postEntities.map(postMapper::postEntityToPost);
    }

    @Override
    public Post findById(Long id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        return postMapper.postEntityToPost(postEntity);
    }

    @Override
    public Post save(Post post) {
        PostEntity postEntity = postMapper.postToPostEntity(post);
        postEntity = postRepository.save(postEntity);
        return postMapper.postEntityToPost(postEntity);
    }

    @Override
    public void deleteById(Long id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException(id);
        }

        postRepository.deleteById(id);
    }

    @Override
    public Post update(Long id, Post updatedPost) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException(id);
        }

        PostEntity updatedEntity = postMapper.postToPostEntity(updatedPost);
        updatedEntity.setId(id);

        postRepository.save(updatedEntity);
        return postMapper.postEntityToPost(updatedEntity);
    }
}
