package com.colegio.prevost.service.delegate.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.AnnouncementDTO;
import com.colegio.prevost.model.Announcement;
import com.colegio.prevost.repository.AnnouncementRepository;
import com.colegio.prevost.service.delegate.AnnouncementDeletage;
import com.colegio.prevost.util.mapper.AnnouncementMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnouncementDeletageImpl implements AnnouncementDeletage {

    private final AnnouncementRepository repository;
    private final AnnouncementMapper mapper;

    @Override
    public AnnouncementDTO getAnnouncementById(String id) {
        Long convertedId = getConvertedId(id);
        return mapper.toDto(repository.findById(convertedId).orElse(null));
    }

    @Override
    public List<AnnouncementDTO> getAllAnnouncements() {
        List<AnnouncementDTO> announcements = new ArrayList<>();
        for (Announcement announcement : repository.findAll()) {
            announcements.add(mapper.toDto(announcement));
        }
        return announcements;
    }

    @Override
    public AnnouncementDTO createAnnouncement(AnnouncementDTO announcement) {
        return mapper.toDto(repository.save(mapper.toEntity(announcement)));
    }

    @Override
    public AnnouncementDTO updateAnnouncement(String id, AnnouncementDTO announcement) {
        AnnouncementDTO entity = getAnnouncementById(id);
     if (entity != null) {
         entity.setDescription(announcement.getDescription());
         entity.setTeacher(announcement.getTeacher());
         entity.setGrade(announcement.getGrade());
         entity.setAnnouncementDate(announcement.getAnnouncementDate());
         return mapper.toDto(repository.save(mapper.toEntity(entity)));
     }
     return null;
    }

    @Override
    public void deleteAnnouncement(String id) {
        Long convertedId = getConvertedId(id);
        repository.deleteById(convertedId);
    }

    @Override
    public List<AnnouncementDTO> findByGradeAndAnnouncementDate(Long studentUserId, LocalDate announcementDate) {
       return repository.findByGradeAndAnnouncementDate(studentUserId, announcementDate).stream()
               .map(mapper::toDto)
               .toList();
    }

    private long getConvertedId(String id) {
        return Long.parseLong(id.substring(8));
    }

}
