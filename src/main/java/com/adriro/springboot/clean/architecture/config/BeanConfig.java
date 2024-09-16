package com.adriro.springboot.clean.architecture.config;

import com.adriro.springboot.clean.architecture.application.mapper.PostMapper;
import com.adriro.springboot.clean.architecture.domain.repository.PostRepository;
import com.adriro.springboot.clean.architecture.domain.service.PostService;
import com.adriro.springboot.clean.architecture.application.service.PostServiceImpl;
import com.adriro.springboot.clean.architecture.application.usecase.*;
import com.adriro.springboot.clean.architecture.infrastructure.persistence.repository.PostJpaRepository;
import com.adriro.springboot.clean.architecture.infrastructure.persistence.repository.PostRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    /**
     * Use cases
     **/

    @Bean
    public CreatePostUseCase createPostUseCase(PostService postService) {
        return new CreatePostUseCase(postService);
    }

    @Bean
    public DeletePostByIdUseCase deletePostByIdUseCase(PostService postService) {
        return new DeletePostByIdUseCase(postService);
    }

    @Bean
    public GetAllPostsUseCase getAllPostsUseCase(PostService postService) {
        return new GetAllPostsUseCase(postService);
    }

    @Bean
    public GetPostByIdUseCase getPostByIdUseCase(PostService postService) {
        return new GetPostByIdUseCase(postService);
    }

    @Bean
    public UpdatePostUseCase updatePostUseCase(PostService postService) {
        return new UpdatePostUseCase(postService);
    }

    /**
     * Repositories
     **/

    @Bean
    public PostRepository postRepository(PostJpaRepository postJpaRepository) {
        return new PostRepositoryImpl(postJpaRepository);
    }

    /**
     * Services
     **/

    @Bean
    public PostService postService(PostRepository postRepository, PostMapper postMapper) {
        return new PostServiceImpl(postRepository, postMapper);
    }

}
