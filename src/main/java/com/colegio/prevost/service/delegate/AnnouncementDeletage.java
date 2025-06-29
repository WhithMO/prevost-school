package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.AnnouncementDTO;

public interface AnnouncementDeletage {

    AnnouncementDTO getAnnouncementById(Long id);
    List<AnnouncementDTO> getAllAnnouncements();
    AnnouncementDTO createAnnouncement(AnnouncementDTO announcement);
    AnnouncementDTO updateAnnouncement(Long id, AnnouncementDTO announcement);
    void deleteAnnouncement(Long id);
}
