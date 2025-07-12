package com.colegio.prevost.controller;

import com.colegio.prevost.dto.AttendanceDTO;
import com.colegio.prevost.service.delegate.AttendanceDeletage;
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
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AttendanceController.class)
class AttendanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttendanceDeletage attendanceDelegate;

    private ObjectMapper objectMapper;

    private AttendanceDTO attendanceDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        attendanceDTO = new AttendanceDTO();
        attendanceDTO.setId(1L);
        attendanceDTO.setPresent(true);
        attendanceDTO.setAttendanceDate(LocalDate.now());
    }

    @Test
    void getAllAttendances() throws Exception {
        given(attendanceDelegate.getAllAttendances()).willReturn(Collections.singletonList(attendanceDTO));

        mockMvc.perform(get("/api/attendances"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].present", is(true)));
    }

    @Test
    void getAttendanceById_whenFound() throws Exception {
        given(attendanceDelegate.getAttendanceById("1")).willReturn(attendanceDTO);

        mockMvc.perform(get("/api/attendances/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void getAttendanceById_whenNotFound() throws Exception {
        given(attendanceDelegate.getAttendanceById("1")).willReturn(null);

        mockMvc.perform(get("/api/attendances/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAttendance() throws Exception {
        given(attendanceDelegate.createAttendance(any(AttendanceDTO.class))).willReturn(attendanceDTO);

        mockMvc.perform(post("/api/attendances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(attendanceDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/attendances/1"));
    }

    @Test
    void updateAttendance_whenFound() throws Exception {
        given(attendanceDelegate.updateAttendance(eq("1"), any(AttendanceDTO.class))).willReturn(attendanceDTO);

        mockMvc.perform(put("/api/attendances/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(attendanceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void updateAttendance_whenNotFound() throws Exception {
        given(attendanceDelegate.updateAttendance(eq("1"), any(AttendanceDTO.class))).willReturn(null);

        mockMvc.perform(put("/api/attendances/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(attendanceDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAttendance() throws Exception {
        doNothing().when(attendanceDelegate).deleteAttendance("1");

        mockMvc.perform(delete("/api/attendances/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getByStudentAndCourse() throws Exception {
        given(attendanceDelegate.findByStudentUserIdAndCourseId(1L, 1L)).willReturn(Collections.singletonList(attendanceDTO));

        mockMvc.perform(get("/api/attendances/student/1/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    void getByStudentCourseAndDate() throws Exception {
        LocalDate date = LocalDate.of(2025, 7, 8);
        given(attendanceDelegate.findByStudentUserIdAndCourseIdAndAttendanceDate(1L, 1L, date))
                .willReturn(Collections.singletonList(attendanceDTO));

        mockMvc.perform(get("/api/attendances/student/1/course/1/date/2025-07-08"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }
}
