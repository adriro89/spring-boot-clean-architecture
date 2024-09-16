package com.adriro.springboot.clean.architecture.application.mapper;

import com.adriro.springboot.clean.architecture.domain.model.Post;
import com.adriro.springboot.clean.architecture.infrastructure.persistence.entity.PostEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostEntity postToPostEntity(Post post);

    Post postEntityToPost(PostEntity postEntity);
}
