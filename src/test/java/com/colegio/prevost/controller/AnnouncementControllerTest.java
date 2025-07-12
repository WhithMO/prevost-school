package com.colegio.prevost.controller;

import com.colegio.prevost.dto.AnnouncementDTO;
import com.colegio.prevost.service.delegate.AnnouncementDeletage;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@WebMvcTest(AnnouncementController.class)
class AnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnnouncementDeletage announcementDelegate;

    private ObjectMapper objectMapper;

    private AnnouncementDTO announcementDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        announcementDTO = new AnnouncementDTO();
        announcementDTO.setId(1L);
        announcementDTO.setDescription("Test Announcement");
        announcementDTO.setAnnouncementDate(LocalDate.now());
    }

    @Test
    void getAllAnnouncements() throws Exception {
        List<AnnouncementDTO> announcements = Collections.singletonList(announcementDTO);
        given(announcementDelegate.getAllAnnouncements()).willReturn(announcements);

        mockMvc.perform(get("/api/announcements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description", is(announcementDTO.getDescription())));
    }

    @Test
    void getAnnouncementById_whenFound() throws Exception {
        given(announcementDelegate.getAnnouncementById("1")).willReturn(announcementDTO);

        mockMvc.perform(get("/api/announcements/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("Test Announcement")));
    }

    @Test
    void getAnnouncementById_whenNotFound() throws Exception {
        given(announcementDelegate.getAnnouncementById("1")).willReturn(null);

        mockMvc.perform(get("/api/announcements/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAnnouncement() throws Exception {
        given(announcementDelegate.createAnnouncement(any(AnnouncementDTO.class))).willReturn(announcementDTO);

        mockMvc.perform(post("/api/announcements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(announcementDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/announcements/1"));
    }

    @Test
    void updateAnnouncement_whenFound() throws Exception {
        given(announcementDelegate.updateAnnouncement(eq("1"), any(AnnouncementDTO.class))).willReturn(announcementDTO);

        mockMvc.perform(put("/api/announcements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(announcementDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Test Announcement")));
    }

    @Test
    void updateAnnouncement_whenNotFound() throws Exception {
        given(announcementDelegate.updateAnnouncement(eq("1"), any(AnnouncementDTO.class))).willReturn(null);

        mockMvc.perform(put("/api/announcements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(announcementDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAnnouncement() throws Exception {
        doNothing().when(announcementDelegate).deleteAnnouncement("1");

        mockMvc.perform(delete("/api/announcements/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getByGradeAndDate() throws Exception {
        List<AnnouncementDTO> announcements = Collections.singletonList(announcementDTO);
        LocalDate date = LocalDate.of(2025, 7, 8);
        given(announcementDelegate.findByGradeAndAnnouncementDate(1L, date)).willReturn(announcements);

        mockMvc.perform(get("/api/announcements/grade/1/date/2025-07-08"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description", is(announcementDTO.getDescription())));
    }
}
