package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.AnnouncementDTO;

public interface AnnouncementDeletage {

    AnnouncementDTO getAnnouncementById(String id);
    List<AnnouncementDTO> getAllAnnouncements();
    AnnouncementDTO createAnnouncement(AnnouncementDTO announcement);
    AnnouncementDTO updateAnnouncement(String id, AnnouncementDTO announcement);
    void deleteAnnouncement(String id);
}
