package com.adriro.springboot.clean.architecture.e2e;

import com.adriro.springboot.clean.architecture.api.model.PostRequestDto;
import com.adriro.springboot.clean.architecture.utils.TestContainersBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerE2ETest extends TestContainersBase {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void testCreatePost() throws Exception {
        PostRequestDto postRequest = new PostRequestDto("Test Title", "Test Content");
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetAllPosts() throws Exception {
        insertPost(1L, "Test Title", "Test Content");
        mockMvc.perform(get("/posts?page=0&size=20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Test Title"))
                .andExpect(jsonPath("$.content[0].content").value("Test Content"))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.numberOfElements").value(1));
    }

    @Test
    void testGetPostById() throws Exception {
        insertPost(1L, "Test Title", "Test Content");
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.content").value("Test Content"));
    }

    @Test
    void testUpdatePost() throws Exception {
        insertPost(1L, "Old Title", "Old Content");
        PostRequestDto postRequest = new PostRequestDto("Updated Title", "Updated Content");
        mockMvc.perform(put("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.content").value("Updated Content"));
    }

    @Test
    void testDeletePost() throws Exception {
        insertPost(1L, "Test Title", "Test Content");
        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreatePostWithInvalidData() throws Exception {
        String invalidPostRequest = "{\n" +
                "  \"title\": \"Test Title\",\n" +
                "  \"content\": \"Test Content\"\n" +
                "  \"notValidField\": \"Test Content\"\n" +
                "}";
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPostRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetPostByIdNotFound() throws Exception {
        mockMvc.perform(get("/posts/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdatePostNotFound() throws Exception {
        PostRequestDto postRequest = new PostRequestDto("Updated Title", "Updated Content");
        mockMvc.perform(put("/posts/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeletePostNotFound() throws Exception {
        mockMvc.perform(delete("/posts/999"))
                .andExpect(status().isNotFound());
    }

    private void insertPost(Long id, String title, String content) throws Exception {
        PostRequestDto postRequest = new PostRequestDto(title, content);
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isCreated());
    }
}
