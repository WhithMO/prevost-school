package com.colegio.prevost.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colegio.prevost.model.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findByGradeAndAnnouncementDate(Long studentUserId, LocalDate announcementDate);
}
