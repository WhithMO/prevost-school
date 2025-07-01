package com.colegio.prevost.service.delegate;

import java.time.LocalDate;
import java.util.List;

import com.colegio.prevost.dto.AnnouncementDTO;
import com.colegio.prevost.model.Announcement;

public interface AnnouncementDeletage {

    AnnouncementDTO getAnnouncementById(String id);
    List<AnnouncementDTO> getAllAnnouncements();
    AnnouncementDTO createAnnouncement(AnnouncementDTO announcement);
    AnnouncementDTO updateAnnouncement(String id, AnnouncementDTO announcement);
    void deleteAnnouncement(String id);
    List<AnnouncementDTO> findByGradeAndAnnouncementDate(Long studentUserId, LocalDate announcementDate);
}
