package com.colegio.prevost.controller;

import com.colegio.prevost.dto.StudentDTO;
import com.colegio.prevost.service.delegate.StudentDeletage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentDeletage studentDelegate;

    private ObjectMapper objectMapper;

    private StudentDTO studentDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        studentDTO = new StudentDTO();
        studentDTO.setUsername("jasmith");
        studentDTO.setNames("Jane");
        studentDTO.setSurNames("Smith");
    }

    @Test
    void getStudentById_whenFound() throws Exception {
        given(studentDelegate.getStudentByUsername("jasmith")).willReturn(studentDTO);

        mockMvc.perform(get("/api/students/jasmith"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.names", is("Jane")));
    }

    @Test
    void createStudent() throws Exception {
        given(studentDelegate.createStudent(any(StudentDTO.class))).willReturn(studentDTO);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDTO)))
                .andExpect(status().isCreated());
    }
}
