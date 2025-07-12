package com.colegio.prevost.controller;

import com.colegio.prevost.dto.IncidentDTO;
import com.colegio.prevost.service.delegate.IncidentDeletage;
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

@WebMvcTest(IncidentController.class)
class IncidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IncidentDeletage incidentDelegate;

    private ObjectMapper objectMapper;

    private IncidentDTO incidentDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        incidentDTO = new IncidentDTO();
        incidentDTO.setId(1L);
        incidentDTO.setDescription("Late to class");
        incidentDTO.setIncidentDate(LocalDate.now());
    }

    @Test
    void getIncidentById_whenFound() throws Exception {
        given(incidentDelegate.getIncidentById("1")).willReturn(incidentDTO);

        mockMvc.perform(get("/api/incidents/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Late to class")));
    }

    @Test
    void createIncident() throws Exception {
        given(incidentDelegate.createIncident(any(IncidentDTO.class))).willReturn(incidentDTO);

        mockMvc.perform(post("/api/incidents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incidentDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void getByStudentAndDate() throws Exception {
        LocalDate date = LocalDate.of(2025, 7, 8);
        given(incidentDelegate.findByStudentUserIdAndIncidentDate(1L, date)).willReturn(Collections.singletonList(incidentDTO));

        mockMvc.perform(get("/api/incidents/student/1/date/2025-07-08"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description", is("Late to class")));
    }
}
