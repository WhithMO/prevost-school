package com.colegio.prevost.controller;

import com.colegio.prevost.dto.WorkerDTO;
import com.colegio.prevost.service.delegate.WorkerDeletage;
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

@WebMvcTest(WorkerController.class)
class WorkerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkerDeletage workerDelegate;

    @Autowired
    private ObjectMapper objectMapper;

    private WorkerDTO workerDTO;

    @BeforeEach
    void setUp() {
        workerDTO = new WorkerDTO();
        workerDTO.setUsername("wsmith");
        workerDTO.setNames("Will");
        workerDTO.setSurNames("Smith");
    }

    @Test
    void getWorkerById_whenFound() throws Exception {
        given(workerDelegate.getWorkerByUsername("wsmith")).willReturn(workerDTO);

        mockMvc.perform(get("/api/workers/wsmith"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.names", is("Will")));
    }

    @Test
    void createWorker() throws Exception {
        given(workerDelegate.createWorker(any(WorkerDTO.class))).willReturn(workerDTO);

        mockMvc.perform(post("/api/workers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workerDTO)))
                .andExpect(status().isCreated());
    }
}
