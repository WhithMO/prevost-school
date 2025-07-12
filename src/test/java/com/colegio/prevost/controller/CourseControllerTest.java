package com.colegio.prevost.controller;

import com.colegio.prevost.dto.CourseDTO;
import com.colegio.prevost.service.delegate.CourseDeletage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseDeletage courseDelegate;

    @Autowired
    private ObjectMapper objectMapper;

    private CourseDTO courseDTO;

    @BeforeEach
    void setUp() {
        courseDTO = new CourseDTO();
        courseDTO.setId(1L);
        courseDTO.setName("Mathematics");
    }

    @Test
    void getAllCourses() throws Exception {
        given(courseDelegate.getAllCourses()).willReturn(Collections.singletonList(courseDTO));

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Mathematics")));
    }

    @Test
    void getCourseById_whenFound() throws Exception {
        given(courseDelegate.getCourseById("1")).willReturn(courseDTO);

        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Mathematics")));
    }

    @Test
    void getCourseById_whenNotFound() throws Exception {
        given(courseDelegate.getCourseById("1")).willReturn(null);

        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCourse() throws Exception {
        given(courseDelegate.createCourse(any(CourseDTO.class))).willReturn(courseDTO);

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/courses/1"));
    }

    @Test
    void updateCourse_whenFound() throws Exception {
        given(courseDelegate.updateCourse(eq("1"), any(CourseDTO.class))).willReturn(courseDTO);

        mockMvc.perform(put("/api/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void updateCourse_whenNotFound() throws Exception {
        given(courseDelegate.updateCourse(eq("1"), any(CourseDTO.class))).willReturn(null);

        mockMvc.perform(put("/api/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCourse() throws Exception {
        doNothing().when(courseDelegate).deleteCourse("1");
        mockMvc.perform(delete("/api/courses/1"))
                .andExpect(status().isNoContent());
    }
}
