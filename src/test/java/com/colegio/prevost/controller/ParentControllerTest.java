package com.colegio.prevost.controller;

import com.colegio.prevost.dto.ParentDTO;
import com.colegio.prevost.service.delegate.ParentDeletage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParentController.class)
class ParentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParentDeletage parentDelegate;

    @Autowired
    private ObjectMapper objectMapper;

    private ParentDTO parentDTO;

    @BeforeEach
    void setUp() {
        parentDTO = new ParentDTO();
        parentDTO.setUsername("jdoe");
        parentDTO.setNames("John");
        parentDTO.setSurNames("Doe");
    }

    @Test
    void getParentById_whenFound() throws Exception {
        given(parentDelegate.getParentByUsername("jdoe")).willReturn(parentDTO);

        mockMvc.perform(get("/api/parents/jdoe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.names", is("John")));
    }

    @Test
    void createParent() throws Exception {
        given(parentDelegate.createParent(any(ParentDTO.class))).willReturn(parentDTO);

        mockMvc.perform(post("/api/parents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parentDTO)))
                .andExpect(status().isCreated());
    }
}
