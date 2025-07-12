package com.colegio.prevost.controller;

import com.colegio.prevost.dto.StudentCourseDTO;
import com.colegio.prevost.service.delegate.StudentCourseDeletage;
import com.colegio.prevost.util.enums.StudentCourseRelationEnum;
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

@WebMvcTest(StudentCourseController.class)
class StudentCourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentCourseDeletage studentCourseDelegate;

    @Autowired
    private ObjectMapper objectMapper;

    private StudentCourseDTO studentCourseDTO;

    @BeforeEach
    void setUp() {
        studentCourseDTO = new StudentCourseDTO();
        studentCourseDTO.setId(1L);
        studentCourseDTO.setStatus(StudentCourseRelationEnum.ENROLLED);
    }

    @Test
    void getStudentCourseById_whenFound() throws Exception {
        given(studentCourseDelegate.getStudentCourseById("1")).willReturn(studentCourseDTO);

        mockMvc.perform(get("/api/student-courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("ENROLLED")));
    }

    @Test
    void createStudentCourse() throws Exception {
        given(studentCourseDelegate.createStudentCourse(any(StudentCourseDTO.class))).willReturn(studentCourseDTO);

        mockMvc.perform(post("/api/student-courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentCourseDTO)))
                .andExpect(status().isCreated());
    }
}
