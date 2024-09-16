package com.adriro.springboot.clean.architecture.application.mapper;

import com.adriro.springboot.clean.architecture.api.model.PostPageResponseDto;
import com.adriro.springboot.clean.architecture.api.model.PostPageResponsePageableDto;
import com.adriro.springboot.clean.architecture.api.model.PostRequestDto;
import com.adriro.springboot.clean.architecture.api.model.PostResponseDto;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostDtoMapper {

    PostResponseDto postToPostResponseDto(Post post);

    Post postRequestDtoToPost(PostRequestDto postRequestDto);

    @Mapping(source = "pageable.sort", target = "sort", qualifiedByName = "sortToString")
    PostPageResponsePageableDto pageableToPageableDto(Pageable pageable);

    default PostPageResponseDto pageToPostPageResponseDto(Page<Post> page) {
        PostPageResponseDto dto = new PostPageResponseDto();
        dto.setContent(page.getContent().stream()
                .map(this::postToPostResponseDto)
                .collect(Collectors.toList()));
        dto.setPageable(pageableToPageableDto(page.getPageable()));
        dto.setTotalElements((int) page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setNumberOfElements(page.getNumberOfElements());
        return dto;
    }

    @Named("sortToString")
    default String sortToString(Sort sort) {
        return sort != null ? sort.toString() : "";
    }
}
