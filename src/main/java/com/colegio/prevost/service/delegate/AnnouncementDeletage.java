package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.model.Announcement;

public interface AnnouncementDeletage {

    Announcement getAnnouncementById(Long id);
    List<Announcement> getAllAnnouncements();
    Announcement createAnnouncement(Announcement announcement);
    Announcement updateAnnouncement(Long id, Announcement announcement);
    void deleteAnnouncement(Long id);
}
