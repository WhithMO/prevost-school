package com.colegio.prevost.service.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.model.Announcement;
import com.colegio.prevost.repository.AnnouncementRepository;
import com.colegio.prevost.service.delegate.AnnouncementDeletage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnouncementDeletageImpl implements AnnouncementDeletage {

    private final AnnouncementRepository repository;

    @Override
    public Announcement getAnnouncementById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Announcement> getAllAnnouncements() {
        return repository.findAll();
    }

    @Override
    public Announcement createAnnouncement(Announcement announcement) {
        return repository.save(announcement);
    }

    @Override
    public Announcement updateAnnouncement(Long id, Announcement announcement) {
     Announcement entity = getAnnouncementById(id);
     if (entity != null) {
         entity.setDescription(announcement.getDescription());
         entity.setTeacher(announcement.getTeacher());
         entity.setGrade(announcement.getGrade());
         return repository.save(entity);
     }
     return null;
    }

    @Override
    public void deleteAnnouncement(Long id) {
        repository.deleteById(id);
    }

}
