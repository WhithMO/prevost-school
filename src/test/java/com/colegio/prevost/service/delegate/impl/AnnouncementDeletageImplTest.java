package com.colegio.prevost.service.delegate.impl;

import com.colegio.prevost.dto.AnnouncementDTO;
import com.colegio.prevost.model.Announcement;
import com.colegio.prevost.repository.AnnouncementRepository;
import com.colegio.prevost.util.mapper.AnnouncementMapper;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void getAnnouncementById_Success() {
        when(repository.findById(longId)).thenReturn(Optional.of(announcement));
        when(mapper.toDto(announcement)).thenReturn(announcementDTO);

        AnnouncementDTO result = delegate.getAnnouncementById(id);

        assertNotNull(result);
        assertEquals(announcementDTO.getDescription(), result.getDescription());
        verify(repository).findById(longId);
    }

    @Test
    void getAnnouncementById_NotFound() {
        when(repository.findById(longId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> delegate.getAnnouncementById(id));
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
    void updateAnnouncement_Success() {
        AnnouncementDTO updatedDto = new AnnouncementDTO();
        updatedDto.setId(longId);
        updatedDto.setDescription("Updated");

        Announcement updatedEntity = Announcement.builder().id(longId).description("Updated").build();

        when(repository.findById(longId)).thenReturn(Optional.of(announcement));
        when(repository.save(any(Announcement.class))).thenReturn(updatedEntity);
        when(mapper.toDto(any(Announcement.class))).thenReturn(updatedDto);

        AnnouncementDTO result = delegate.updateAnnouncement(id, updatedDto);

        assertNotNull(result);
        assertEquals("Updated", result.getDescription());
        verify(repository).save(any(Announcement.class));
    }

    @Test
    void updateAnnouncement_NotFound() {
        when(repository.findById(longId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> delegate.updateAnnouncement(id, announcementDTO));
    }

    @Test
    void deleteAnnouncement_Success() {
        when(repository.existsById(longId)).thenReturn(true);
        doNothing().when(repository).deleteById(longId);

        delegate.deleteAnnouncement(id);

        verify(repository).deleteById(longId);
    }

    @Test
    void deleteAnnouncement_NotFound() {
        when(repository.existsById(longId)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> delegate.deleteAnnouncement(id));
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
