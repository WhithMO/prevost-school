package com.colegio.prevost.controller;

import com.colegio.prevost.dto.ParentStudentDTO;
import com.colegio.prevost.service.delegate.ParentStudentDeletage;
import com.colegio.prevost.util.enums.RelationshipEnum;
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

@WebMvcTest(ParentStudentController.class)
class ParentStudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParentStudentDeletage delegate;

    @Autowired
    private ObjectMapper objectMapper;

    private ParentStudentDTO parentStudentDTO;

    @BeforeEach
    void setUp() {
        parentStudentDTO = new ParentStudentDTO();
        parentStudentDTO.setId(1L);
        parentStudentDTO.setRelationship(RelationshipEnum.FATHER);
    }

    @Test
    void getParentStudentById_whenFound() throws Exception {
        given(delegate.getParentStudentById("1")).willReturn(parentStudentDTO);

        mockMvc.perform(get("/api/parent-students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.relationship", is("FATHER")));
    }

    @Test
    void createParentStudent() throws Exception {
        given(delegate.createParentStudent(any(ParentStudentDTO.class))).willReturn(parentStudentDTO);

        mockMvc.perform(post("/api/parent-students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parentStudentDTO)))
                .andExpect(status().isCreated());
    }
}
