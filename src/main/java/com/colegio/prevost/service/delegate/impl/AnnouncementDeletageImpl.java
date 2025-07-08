package com.colegio.prevost.service.delegate.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.AnnouncementDTO;
import com.colegio.prevost.model.Announcement;
import com.colegio.prevost.repository.AnnouncementRepository;
import com.colegio.prevost.service.delegate.AnnouncementDeletage;
import com.colegio.prevost.util.mapper.AnnouncementMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementDeletageImpl implements AnnouncementDeletage {

    private final AnnouncementRepository repository;
    private final AnnouncementMapper mapper;

    @Override
    public AnnouncementDTO getAnnouncementById(String id) {
        Long convertedId = getConvertedId(id);
        if (!repository.existsById(convertedId)) {
            throw new EntityNotFoundException("Announcement not found with id " + id);
        }
        return mapper.toDto(repository.findById(convertedId).orElse(null));
    }

    @Override
    public List<AnnouncementDTO> getAllAnnouncements() {
        try {
            List<AnnouncementDTO> announcements = new ArrayList<>();
            for (Announcement announcement : repository.findAll()) {
                announcements.add(mapper.toDto(announcement));
            }
            return announcements;
        } catch (Exception e) {
            log.error("Error de acceso a datos al listar Announcement", e);
            throw new ServiceException("Error interno al listar la solicitud");
        }
    }

    @Override
    public AnnouncementDTO createAnnouncement(AnnouncementDTO announcement) {
        try {
            return mapper.toDto(repository.save(mapper.toEntity(announcement)));
        }  catch (Exception dae) {
            log.error("Error de acceso a datos al crear Announcement", dae);
            throw new ServiceException("Error interno al crear la solicitud");
        }
    }

    @Override
    public AnnouncementDTO updateAnnouncement(String id, AnnouncementDTO announcement) {
        AnnouncementDTO entity = getAnnouncementById(id);
        try {
            if (entity == null) {
                throw new ServiceException("El anuncio no existe");
            }
            entity.setDescription(announcement.getDescription());
            entity.setTeacher(announcement.getTeacher());
            entity.setGrade(announcement.getGrade());
            entity.setAnnouncementDate(announcement.getAnnouncementDate());
            return mapper.toDto(repository.save(mapper.toEntity(entity)));
        } catch (Exception e) {
            log.error("Error de acceso a datos al actualizar Announcement id={}", id, e);
            throw new ServiceException("Error interno al actualizar la solicitud");
        }
    }

    @Override
    public void deleteAnnouncement(String id) {
        try {
            Long convertedId = getConvertedId(id);
            if (!repository.existsById(convertedId)) {
                throw new ServiceException("Recurso no encontrado: Announcement id=" + id);
            }
            repository.deleteById(convertedId);
        } catch (Exception e) {
            log.error("Error de acceso a datos al eliminar Announcement id={}", id, e);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public List<AnnouncementDTO> findByGradeAndAnnouncementDate(Long studentUserId, LocalDate announcementDate) {
        try {
            return repository.findByGradeAndAnnouncementDate(studentUserId, announcementDate).stream()
                    .map(mapper::toDto)
                    .toList();
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al buscar Announcement para grade={} AnnouncementDate={}", studentUserId, announcementDate, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    private long getConvertedId(String id) {
        return Long.parseLong(id.substring(8));
    }

}
