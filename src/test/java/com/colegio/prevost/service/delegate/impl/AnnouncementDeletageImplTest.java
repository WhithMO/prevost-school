package com.colegio.prevost.service.delegate.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import com.colegio.prevost.dto.AnnouncementDTO;
import com.colegio.prevost.model.Announcement;
import com.colegio.prevost.repository.AnnouncementRepository;
import com.colegio.prevost.util.mapper.AnnouncementMapper;

@ExtendWith(MockitoExtension.class)
class AnnouncementDeletageImplTest {

    @Mock
    private AnnouncementRepository repository;

    @Mock
    private AnnouncementMapper mapper;

    @InjectMocks
    private AnnouncementDeletageImpl delegate;

    private Announcement announcement;
    private AnnouncementDTO announcementDTO;
    private final String id = "1";
    private final Long longId = 1L;

    @BeforeEach
    void setUp() {
        announcement = Announcement.builder().id(longId).description("Test").build();
        announcementDTO = new AnnouncementDTO();
        announcementDTO.setId(longId);
        announcementDTO.setDescription("Test");
    }

    @Test
    void getAllAnnouncements_Success() {
        when(repository.findAll()).thenReturn(Collections.singletonList(announcement));
        when(mapper.toDto(any(Announcement.class))).thenReturn(announcementDTO);

        List<AnnouncementDTO> results = delegate.getAllAnnouncements();

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        verify(repository).findAll();
    }

    @Test
    void getAllAnnouncements_DataAccessException() {
        when(repository.findAll()).thenThrow(new DataAccessResourceFailureException("DB down"));
        assertThrows(ServiceException.class, () -> delegate.getAllAnnouncements());
    }

    @Test
    void createAnnouncement_Success() {
        when(mapper.toEntity(announcementDTO)).thenReturn(announcement);
        when(repository.save(announcement)).thenReturn(announcement);
        when(mapper.toDto(announcement)).thenReturn(announcementDTO);

        AnnouncementDTO result = delegate.createAnnouncement(announcementDTO);

        assertNotNull(result);
        assertEquals(longId, result.getId());
        verify(repository).save(announcement);
    }

    @Test
    void findByGradeAndAnnouncementDate_Success() {
        LocalDate date = LocalDate.now();
        when(repository.findByGradeAndAnnouncementDate(1L, date)).thenReturn(Collections.singletonList(announcement));
        when(mapper.toDto(announcement)).thenReturn(announcementDTO);

        List<AnnouncementDTO> results = delegate.findByGradeAndAnnouncementDate(1L, date);

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        verify(repository).findByGradeAndAnnouncementDate(1L, date);
    }
}
