package com.colegio.prevost.controller;

import com.colegio.prevost.dto.GradeRecordDTO;
import com.colegio.prevost.service.delegate.GradeRecordDeletage;
import com.colegio.prevost.util.enums.EvaluationEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GradeRecordController.class)
class GradeRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GradeRecordDeletage gradeRecordDelegate;

    private ObjectMapper objectMapper;

    private GradeRecordDTO gradeRecordDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        gradeRecordDTO = new GradeRecordDTO();
        gradeRecordDTO.setId(1L);
        gradeRecordDTO.setScore(95.5);
        gradeRecordDTO.setEvaluationType(EvaluationEnum.FIRST_HOMEWORK);
        gradeRecordDTO.setEvaluationDate(LocalDate.now());
    }

    @Test
    void getGradeRecordById_whenFound() throws Exception {
        given(gradeRecordDelegate.getGradeRecordById("1")).willReturn(gradeRecordDTO);

        mockMvc.perform(get("/api/grade-records/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score", is(95.5)));
    }

    @Test
    void createGradeRecord() throws Exception {
        given(gradeRecordDelegate.createGradeRecord(any(GradeRecordDTO.class))).willReturn(gradeRecordDTO);

        mockMvc.perform(post("/api/grade-records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gradeRecordDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void getByStudentAndCourse() throws Exception {
        given(gradeRecordDelegate.findByStudentUserIdAndCourseId(1L, 1L)).willReturn(Collections.singletonList(gradeRecordDTO));

        mockMvc.perform(get("/api/grade-records/student/1/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].score", is(95.5)));
    }
}
