package com.adriro.springboot.clean.architecture.application.unit.mapper;

import com.adriro.springboot.clean.architecture.api.model.PostPageResponseDto;
import com.adriro.springboot.clean.architecture.api.model.PostPageResponsePageableDto;
import com.adriro.springboot.clean.architecture.api.model.PostRequestDto;
import com.adriro.springboot.clean.architecture.api.model.PostResponseDto;
import com.adriro.springboot.clean.architecture.application.mapper.PostDtoMapper;
import com.adriro.springboot.clean.architecture.domain.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PostDtoMapperTest {

    private PostDtoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(PostDtoMapper.class);
    }

    @Test
    void testPostToPostResponseDto() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test Title");
        post.setContent("Test Content");

        PostResponseDto responseDto = mapper.postToPostResponseDto(post);

        assertNotNull(responseDto);
        assertEquals(post.getId(), responseDto.getId());
        assertEquals(post.getTitle(), responseDto.getTitle());
        assertEquals(post.getContent(), responseDto.getContent());
    }

    @Test
    void testPostRequestDtoToPost() {
        PostRequestDto requestDto = new PostRequestDto();
        requestDto.setTitle("Test Title");
        requestDto.setContent("Test Content");

        Post post = mapper.postRequestDtoToPost(requestDto);

        assertNotNull(post);
        assertEquals(requestDto.getTitle(), post.getTitle());
        assertEquals(requestDto.getContent(), post.getContent());
    }

    @Test
    void testPageableToPageableDto() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("title").descending());

        PostPageResponsePageableDto pageableDto = mapper.pageableToPageableDto(pageable);

        assertNotNull(pageableDto);
        assertEquals(1, pageableDto.getPageNumber());
        assertEquals(10, pageableDto.getPageSize());
        assertEquals("title: DESC", pageableDto.getSort());
    }

    @Test
    void testPageToPostPageResponseDto() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test Title");
        post.setContent("Test Content");

        Page<Post> page = new PageImpl<>(Collections.singletonList(post), PageRequest.of(0, 10), 1);

        PostPageResponseDto responseDto = mapper.pageToPostPageResponseDto(page);

        assertNotNull(responseDto);
        assertEquals(1, responseDto.getTotalElements());
        assertEquals(1, responseDto.getTotalPages());
        assertEquals(1, responseDto.getNumberOfElements());
        assertEquals(0, responseDto.getPageable().getPageNumber());
        assertEquals(10, responseDto.getPageable().getPageSize());
        assertEquals(1, responseDto.getContent().size());
        assertEquals(post.getId(), responseDto.getContent().get(0).getId());
    }

    @Test
    void testSortToString() {
        Sort sort = Sort.by("title").descending();
        String sortString = mapper.sortToString(sort);

        assertNotNull(sortString);
        assertEquals("title: DESC", sortString);

        // Test null sort
        sortString = mapper.sortToString(null);
        assertEquals("", sortString);
    }
}
